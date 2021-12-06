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

import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.random.SingleResult;
import net.unknowndomain.alea.roll.LocalizedResult;

/**
 *
 * @author journeyman
 */
public class ShintiaraResults extends LocalizedResult
{
    private final static String BUNDLE_NAME = "net.unknowndomain.alea.systems.blacksad.RpgSystemBundle";
    
    private final SingleResult<Integer> result;
    private final SingleResult<Integer> assetResult;
    private boolean autoSuccess;
    private boolean criticalSuccess;
    private boolean criticalFailure;
    private boolean success;
    private boolean showAsset = false;
    
    public ShintiaraResults(SingleResult<Integer> result, SingleResult<Integer> assetResult)
    {
        this.result = result;
        this.assetResult = assetResult;
    }

    public boolean isAutoSuccess()
    {
        return autoSuccess;
    }

    public void setAutoSuccess(boolean autoSuccess)
    {
        this.autoSuccess = autoSuccess;
    }

    public boolean isCriticalSuccess()
    {
        return criticalSuccess;
    }

    public void setCriticalSuccess(boolean criticalSuccess)
    {
        this.criticalSuccess = criticalSuccess;
    }

    public boolean isCriticalFailure()
    {
        return criticalFailure;
    }

    public void setCriticalFailure(boolean criticalFailure)
    {
        this.criticalFailure = criticalFailure;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public SingleResult<Integer> getResult()
    {
        return result;
    }

    public SingleResult<Integer> getAssetResult()
    {
        return assetResult;
    }

    @Override
    protected void formatResults(MsgBuilder messageBuilder, boolean verbose, int indentValue)
    {
        if (isAutoSuccess())
        {
            messageBuilder.append(translate("shintiara.results.autoSuccess"));
        }
        else if (isCriticalSuccess())
        {
            messageBuilder.append(translate("shintiara.results.criticalSuccess"));
        }
        else if (isCriticalFailure())
        {
            messageBuilder.append(translate("shintiara.results.criticalFailure"));
        }
        else if (isSuccess())
        {
            messageBuilder.append(translate("shintiara.results.success"));
        }
        else
        {
            messageBuilder.append(translate("shintiara.results.failure"));
        }
        messageBuilder.appendNewLine();
        if (verbose)
        {
            messageBuilder.append("Roll ID: ").append(getUuid()).appendNewLine();
            messageBuilder.append(translate("shintiara.results.diceResults")).append(getResult().getLabel()).append(" => ").append(getResult().getValue());
            if (showAsset)
            {
                messageBuilder.append(translate("shintiara.results.asset", (getAssetResult().getValue() * 10)));
            }
            messageBuilder.appendNewLine();
        }
    }

    public boolean isShowAsset()
    {
        return showAsset;
    }

    public void setShowAsset(boolean showAsset)
    {
        this.showAsset = showAsset;
    }

    @Override
    protected String getBundleName()
    {
        return BUNDLE_NAME;
    }

}
