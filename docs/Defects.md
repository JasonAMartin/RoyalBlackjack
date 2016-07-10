## DEFECTS

1. user can bet unlimited money for Royal Match
* [Fixed] 0.2.1

2. If user bets more than they have for hand, it doesn't let them know the bet failed.
* [Fixed] 0.2.1 - I introduced a slight defect where user may get double messaging for placing bets, but this will be fixed once the code is moved into the GameOperations class.

3. When user types in a name that's more than one word, only the first word is used. Should use it all.
* [Fixed] 0.2.1