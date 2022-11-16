# Introduction
"Interactions" is a minecraft spigot plugin for adding actions with conditions.

# Command
* /its reload  
  Reload all the config under plugins/Interactions
* /its actions  
  List all the Actions
* /its action <ActionName>  
  Show this action's config sctructure
* /its conditions  
  List add the Conditions
* /its condition <ConditionName>  
  Show this condition's config structure

# Example
```
actions:
  AttackDamageManipulate:
    amountD: 2.0
    conditions:
      AttackDamage:
        threshold: 10.0
        smaller: false
      ContainsItem:
        slot: head
        item: GLASS
  BlockDrop:
    itemStack:
      item: DIAMOND
      amount: 2
    conditions:
      PlayerEffects:
        effectType: LUCK
      Chance:
        percentage: 0.5
  
```

# Last
The lasted version's source code will be open source, only the last version will. Sorry.
