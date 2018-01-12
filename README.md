# Investment-portfolio

+ This program is about an user interface for an investment portfolio.

+ There is 2 ways to run the program:
1. Run with no arguments
2. Run with arguments. Ideally args[0] = Investments.txt

+When quiting the user interface, an output file named Investments.txt should be created if no argument was given, otherwise take the argument given on the command line.

+ After running the program, you are expected to see the first interface "Welcome to the Investment Portfolio"

+ Clicking on the Commands from the menu bar, you have a selection of options

+ Buy Interface:
-No fields cannot be empty and quantity and price are checked for correct format.
-Reset button- clear all fields
-Buy - Add new investment to portfolio or update existing one

+ Sell Interface:
-Fields cannot be empty and and quantity and price are checked for correct format.
-Clicking on "Sell" when portfolio is empty, will result in displaying a message about that but if portfolio is not empty, investment is sold and necessary updates are made
-Reset button - clear al fields

+ Update Interface
-Case 1: No input file/No investments:
All 3 buttons are disabled, therefore user has to add new investments first. 
-Case 2: When starting program, file was read and investments are uploaded:
User can use prev and next buttons to go through investments
-Save buttons- To save the changes made to the price

+ GetGain Interface
-If portfolio is empty, total gain is 0.0, otherwise once getGain option is chosen, it will display the total gain and also the list of investments where user can scrolled through

+ Search Interface
NOTE FOR PRICE:
* if low price == 0.0 and high price == 0.00, means we are not searching based on price

* if low price == 0.0 and high price == x, where x is a double, thus we are searching for investments in between 
0.0 <= price <= x

* if low price == x and high price == 0.0, then we are looking for an investment of exact price x

* if low price == x and high price == y, then we are looking for investments in the range of x <= price <= y

-If portfolio is empty, search button is disabled
-Otherwise, users can fill in the fields or leave empty depending on the type of search they want
-Search button- Will display the investments found based on the criteria search

+ Quit Option
-Will close the interface and save all the investments


Things to fix or add:
Gain button does not work, and list of all the gains do not appear
Use of abstract class






