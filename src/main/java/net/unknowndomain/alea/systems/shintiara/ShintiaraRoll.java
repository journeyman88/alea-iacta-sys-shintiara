/*
 * Copyright 2020 Marco Bignami.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.unknowndomain.alea.systems.shintiara;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.unknowndomain.alea.random.SingleResult;
import net.unknowndomain.alea.random.dice.bag.D10;
import net.unknowndomain.alea.random.dice.bag.D100;
import net.unknowndomain.alea.roll.GenericResult;
import net.unknowndomain.alea.roll.GenericRoll;

/**
 *
 * @author journeyman
 */
public class ShintiaraRoll implements GenericRoll
{
    
    private final int target;
    private final int netVantage;
    private final Set<ShintiaraModifiers> mods;
    
    public ShintiaraRoll(Integer target, Integer advantage, Integer disadvantage, ShintiaraModifiers ... mod)
    {
        this(target, advantage, disadvantage, Arrays.asList(mod));
    }
    
    public ShintiaraRoll(Integer target, Integer advantage, Integer disadvantage, Collection<ShintiaraModifiers> mod)
    {
        this.mods = new HashSet<>();
        if (mod != null)
        {
            this.mods.addAll(mod);
        }
        if ((advantage == null) || (advantage < 0))
        {
            advantage = 0;
        }
        else if (advantage > 9)
        {
            advantage = 9;
        }
        if ((disadvantage == null) || (disadvantage < 0))
        {
            disadvantage = 0;
        }
        else if (disadvantage > 9)
        {
            disadvantage = 9;
        }
        this.netVantage = advantage - disadvantage;
        this.target = target;
    }
    
    @Override
    public GenericResult getResult()
    {
        SingleResult<Integer> assetDice = new SingleResult<>("d10 -1", D10.INSTANCE.nextResult().get().getValue() - 1);
        ShintiaraResults results = buildResults(D100.INSTANCE.nextResult().get(), assetDice);
        results.setShowAsset(netVantage != 0);
        results.setVerbose(mods.contains(ShintiaraModifiers.VERBOSE));
        return results;
    }
    
    private ShintiaraResults buildResults(SingleResult<Integer> result, SingleResult<Integer> assetDice)
    {
        ShintiaraResults results = new ShintiaraResults(result, assetDice);
        BigDecimal tgt = new BigDecimal(target);
        int criticalThreshold = tgt.divide(new BigDecimal(2), 0, RoundingMode.CEILING).intValue();
        if (result.getValue() == 1)
        {
            results.setAutoSuccess(true);
        }
        if (result.getValue() <= criticalThreshold)
        {
            results.setCriticalSuccess(true);
        }
        if (result.getValue() >= 95)
        {
            results.setCriticalFailure(true);
            results.setSuccess(false);
        }
        else
        {
            int vantage = 0;
            if (netVantage != 0)
            {
                vantage = (Math.abs(netVantage) < assetDice.getValue()) ? Math.abs(netVantage) : assetDice.getValue(); 
                vantage = (netVantage > 0) ? vantage * -10 : vantage * 10;
            }
            int adjRes = result.getValue() + vantage;
            results.setSuccess((adjRes <= target));
        }
        return results;
    }
}
