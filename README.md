# alea-iacta-sys-shintiara
A RPG system module for Alea Iacta Est implementing Shintiara RPG - 1st Edition

## Description
This command will roll a single d100 checking the result against the target and against the eventual critical range. Eventually - if an advantage or a disvantage is passed along - it will also roll the asset dice (d10) and use it to transform the result according to the Modern d100 rules.

### Roll modifiers
Passing these parameters, the associated modifier will be enabled:

* `-v` : Will enable a more verbose mode that will show a detailed version of every result obtained in the roll.

## Help print
```
Shintiara RPG [ shintiara | shn ]

Usage: shn -t <targetValue>
   or: shn -t <targetValue> -d <disadvantageValue>

Description:
This command will roll a single d100 checking the result against the target
and against the eventual critical range. Eventually - if an advantage or a
disvantage is passed along - it will also roll the asset dice (d10) and use
it to transform the result according to the Modern d100 rules.

Options:
  -t, --target=targetValue   Target to achieve success
  -a, --advantage=advantageValue
                             Advantage rank, between 0 and 9
  -d, --disadvantage=disadvantageValue
                             Disadvantage rank, between 0 and 9
  -h, --help                 Print the command help
  -v, --verbose              Enable verbose output

```
