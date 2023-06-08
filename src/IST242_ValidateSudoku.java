//--------------------------------------------------------------------------------------
//--
//--    Matthew Covone, IST 242 Section 001 [Fall 2022 Semester]
//--
//--    The purpose of this program is to see if a certain sudoku solution is valid.
//--    In order for a sudoku solution to be valid, it needs to run through and pass
//--    all the validation checks pertaining to any duplicate values found in any of
//--    the rows, columns, and 3x3 sections.
//--------------------------------------------------------------------------------------

public class IST242_ValidateSudoku
{

    /**
     * The amount of rows in a sudoku puzzle
     */
    private static final int ROW_SIZE = 9;

    /**
     * The amount of columns in a sudoku puzzle
     */
    private static final int COL_SIZE = 9;

    /**
     * The amount of 3x3 sections in a sudoku puzzle
     */
    private static final int SECTION_SIZE = 9;

    // The driver of the program
    public static void main(String[] args)
    {
        /*
        {SUDOKU DOCUMENTATION THAT THE USER SHOULD KNOW ABOUT}

        > Sudoku row set-up:     Rows 1 to 9,    from top to bottom
        > Sudoku column set-up:  Columns 1 to 9, from left to right
        > Sudoku Array section (from 1 to 9) set-up:

         Section | Section | Section
            1    |    2    |    3
        -----------------------------
         Section | Section | Section
            4    |    5    |    6
        -----------------------------
         Section | Section | Section
            7    |    8    |    9

        */

        // Hard-coded array (This should be a valid sudoku solution)
        int[][] sudokuPuzzle =
                {
                        {6, 9, 4,    1, 7, 5,   8, 2, 3},
                        {3, 5, 7,    2, 4, 8,   1, 6, 9},
                        {8, 1, 2,    3, 6, 9,   7, 5, 4},

                        {1, 3, 5,    6, 2, 7,   9, 4, 8},
                        {9, 4, 6,    8, 1, 3,   2, 7, 5},
                        {7, 2, 8,    5, 9, 4,   6, 3, 1},

                        {2, 6, 9,    4, 5, 1,   3, 8, 7},
                        {5, 8, 1,    7, 3, 6,   4, 9, 2},
                        {4, 7, 3,    9, 8, 2,   5, 1, 6},
                };

        // Hard-coded array
        // (This should be an invalid sudoku solution due to a duplicate in one of the 3x3 sections. What makes this one
        // special is that it passes the row and column duplication tests before doing the dupe test for the section.)
        /*
        int[][] sudokuPuzzle =
                {
                        {1, 2, 3,    4, 5, 6,    7, 8, 9},
                        {2, 3, 4,    5, 6, 7,    8, 9, 1},
                        {3, 4, 5,    6, 7, 8,    9, 1, 2},

                        {4, 5, 6,    7, 8, 9,    1, 2, 3},
                        {5, 6, 7,    8, 9, 1,    2, 3, 4},
                        {6, 7, 8,    9, 1, 2,    3, 4, 5},

                        {7, 8, 9,    1, 2, 3,    4, 5, 6},
                        {8, 9, 1,    2, 3, 4,    5, 6, 7},
                        {9, 1, 2,    3, 4, 5,    6, 7, 8},
                };
        */

        int duplicationCatchSuccessTotal = 0;
        int[] temp = new int[9];
        boolean numOutOfBounds = false;

        //---------------------------------------------------------------------------------------------------------
        // Prints out the sudoku puzzle. Also runs a secret input test to see if all numbers are only 1 - 9

        // (Could've used StringBuilder or "Arrays.toString" for this part, but this still
        // accomplishes the goal of printing out the puzzle solution in a nice and readable format.)
        //---------------------------------------------------------------------------------------------------------

        System.out.println("--------------------------------------------------------\nThe hard-coded sudoku " +
                "puzzle array:\n--------------------------------------------------------\n");

        // "For each row"
        for (int rowIdx = 0; rowIdx < ROW_SIZE; rowIdx++)
        {
            // "For each column space in a row"
            for (int colIdx = 0; colIdx < COL_SIZE; colIdx++)
            {
                // "After every three elements, print out a space and divider"
                if ((colIdx % 3 == 0) && colIdx != 0)
                {
                    System.out.print("  |  ");
                }

                // "At the beginning of each group of three elements, print out some blank space before it"
                else if ((colIdx % 3 == 0))
                {
                    System.out.print("  ");
                }

                // "If any number in the solution is NOT between 1 - 9, raise a flag"
                if ((sudokuPuzzle[rowIdx][colIdx] < 1) || (sudokuPuzzle[rowIdx][colIdx] > 9))
                {
                    numOutOfBounds = true;
                }

                // Prints out the current element in the current row
                System.out.print(" " + sudokuPuzzle[rowIdx][colIdx] + " ");
            }

            // Prints out an empty to start printing out the next row of the sudoku array
            System.out.println();

            // Prints out a line after rows 2 and 5
            if ((rowIdx == 2) || (rowIdx == 5))
            {
                System.out.println("-----------------------------------------");
            }
        }

        // "If all numbers in the solution are 1 - 9..." (Note: All inputs need to be 1 - 9 to run the program.)
        if (!numOutOfBounds)
        {
            //---------------------------------------------------------------------------------------------------------
            // Row duplication check
            //---------------------------------------------------------------------------------------------------------
            int rowCheckID = 1;

            System.out.println("\n--------------------------------------------------------\nRow duplication " +
                    "check:\n--------------------------------------------------------");

            // "For each row"
            for (int rowIdx = 0; rowIdx < ROW_SIZE; rowIdx++)
            {
                // "For each column space in a row" (Put all the elements of the row in an array)
                for (int colIdx = 0; colIdx < COL_SIZE; colIdx++)
                {
                    temp[colIdx] = sudokuPuzzle[rowIdx][colIdx];
                }

                // Call duplication check test and clear out current array contents for the next row
                duplicationCatchSuccessTotal = dupeCheckPrint(temp, rowIdx, rowCheckID) + duplicationCatchSuccessTotal;
                temp = new int[9];
            }

            // If the dupe detection test was successful, print out the error message that lets the user know that the
            // solution is NOT valid as at least one duplicate was found during the check
            if (duplicationCatchSuccessTotal > 0)
            {
                System.out.println("\n" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                        "~~~~~~~~~~~~~~~~~~~~~\nOh no! There was at least one duplicate found in one or more rows!" +
                        " Sudoku solution is NOT VALID\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }

            // If the dupe detection test was NOT successful, print out a success message that lets the user know
            // that all rows were valid
            else
            {
                System.out.println("\n" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                        "~~~~~~~~~~~~~~~~~~~~~\nHooray! You passed the row duplication check! All of the rows were " +
                        "VALID!\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                        "~~~~~~~~~~~~~~~~~~~");
            }

            //---------------------------------------------------------------------------------------------------------
            // Column duplication check.
            //---------------------------------------------------------------------------------------------------------
            int columnCheckID = 2;
            duplicationCatchSuccessTotal = 0;

            System.out.println("\n-----------------------------------------------------------\nColumn duplication " +
                    "check:\n-----------------------------------------------------------");

            // "For each column"
            for (int colIdx = 0; colIdx < COL_SIZE; colIdx++)
            {
                // "For each row space in a column" (Put all the elements of the column in an array)
                for (int rowIdx = 0; rowIdx < ROW_SIZE; rowIdx++)
                {
                    temp[rowIdx] = sudokuPuzzle[rowIdx][colIdx];
                }

                // Call duplication check test and clear out current array contents for the next column
                duplicationCatchSuccessTotal = dupeCheckPrint(temp, colIdx, columnCheckID) + duplicationCatchSuccessTotal;
                temp = new int[9];
            }

            // If the dupe detection test was successful, print out the error message that lets the user know that the
            // solution is NOT valid as at least one duplicate was found during the check
            if (duplicationCatchSuccessTotal > 0)
            {
                System.out.println("\n" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                        "~~~~~~~~~~~~~~~~~~~~~\nOh no! There was at least one duplicate found in one or more columns!" +
                        " Sudoku solution is NOT VALID\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }

            // If the dupe detection test was successful, print out the error message that lets the user know that the
            // solution is NOT valid as at least one duplicate was found during the check
            else
            {
                System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                        "~~~~~~~~~~~~~~~~~~~~~~\nHooray! You passed the columns duplication check! All of the columns " +
                        "were VALID!\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                        "~~~~~~~~~~~~~~~~~~~~~~~");
            }

            //---------------------------------------------------------------------------------------------------------
            // 3x3 section duplication check.
            //---------------------------------------------------------------------------------------------------------
            int sectionCheckID = 3;
            int loopCount;
            duplicationCatchSuccessTotal = 0;

            System.out.println("\n-----------------------------------------------------------\n3x3 section duplication " +
                    "check:\n-----------------------------------------------------------");

            // "For every 3x3 section"
            for (int sectionIdx = 0; sectionIdx < SECTION_SIZE; sectionIdx++)
            {
                loopCount = 0;

                // "For every row in the 3x3 block"
                for (int rowIdx = (sectionIdx / 3) * 3; rowIdx < (((sectionIdx / 3) * 3) + 3); rowIdx++)
                {
                    // "For every cell within a certain row within the 3x3 block" (Put all the elements of the section in an array)
                    for (int colIdx = (sectionIdx % 3) * 3; colIdx < (((sectionIdx % 3) * 3) + 3); colIdx++)
                    {
                        temp[loopCount] = sudokuPuzzle[rowIdx][colIdx];
                        loopCount++;
                    }
                }

                // Call duplication check test and clear out current array contents for the next section
                duplicationCatchSuccessTotal = dupeCheckPrint(temp, sectionIdx, sectionCheckID) + duplicationCatchSuccessTotal;
                temp = new int[9];
            }

            // If the dupe detection test was successful, print out the error message that lets the user know that the
            // solution is NOT valid as at least one duplicate was found during the check
            if (duplicationCatchSuccessTotal > 0)
            {
                System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                        "~~~~~~~~~~~~~~~~~~~~~~~\nOh no! There was at least one duplicate found in one or more 3x3 " +
                        "sections! Sudoku solution is NOT VALID\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }

            // If the dupe detection test was successful, print out the error message that lets the user know that the
            // solution is NOT valid as at least one duplicate was found during the check
            else
            {
                System.out.println("\n" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                        "~~~~~~~~~~~~~~~~~~~~~~\nHooray! You passed the 3x3 duplication check! All of the sections were " +
                        "VALID! The solution is also VALID!\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }
        // "If there is at least one number that isn't between 1 - 9..." (Program refuses to run)
        else
        {
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                    "~~~~~~~~~~~~~~~~~~~~~~\nERROR:\nThere is a number in the solution that isn't " +
                    "between 1 - 9. Please resolve this error and try again.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }

        System.out.println("\nTerminating program...");
    }

    /**
     * @param puzzleArray The current row/column/section array that was passed down from the check
     * @param checkLoopNum The current loop number of the row/column/section array that was passed down from the check
     * @param checkID The ID number associated with the row/column/section duplication tests
     * @return Integer that signifies whether the program went into the dupeFlag statement or not
     */
    public static int dupeCheckPrint ( int[] puzzleArray, int checkLoopNum, int checkID)
    {
        {
            int swapTemp;

            // Sort the array. This would make it much easier to recognize duplicates that
            // occur more than once (Example: The 3s and 4s in the array {1, 2, 2, 2, 3, 4, 4, 4, 5})
            for (int i = 0; i < puzzleArray.length; i++)
            {
                for (int j = i + 1; j < puzzleArray.length; j++)
                {
                    // Swap elements that are not in order
                    if (puzzleArray[i] > puzzleArray[j])
                    {
                        swapTemp = puzzleArray[i];
                        puzzleArray[i] = puzzleArray[j];
                        puzzleArray[j] = swapTemp;
                    }
                }
            }

            int oneKindOfDupeTotal = 0;
            int previousIdx = -1;
            int listCount = 0;

            int[] dupeRecord = new int[4];
            boolean dupeFlag = false;

            // Traverse through the sorted array
            for (int i = 0; i < puzzleArray.length ; ++i)
            {

                // "If the current element is the same as the one from the last index"
                if (puzzleArray[i] == previousIdx)
                {
                    // Upon reaching this statement, set this flag to true as it shows that at least
                    // one duplicate was encountered when traversing through the sorted array
                    dupeFlag = true;

                    // A Duplicate element of the previous one was detected
                    ++oneKindOfDupeTotal;

                    // If a duplicated number of the previous element was detected. However,
                    // If the same previous element has more than one duplicate, it won't pass onto the next if statement
                    if (oneKindOfDupeTotal == 1)
                    {
                        // Records the unique duplicate value onto a separate array
                        dupeRecord[listCount] = puzzleArray[i];
                        listCount++;
                    }
                }
                // Previous element becomes current. Restarts dupe detection count process
                else
                {
                    previousIdx = puzzleArray[i];
                    oneKindOfDupeTotal = 0;
                }
            }

            // "If there was at least one duplicate that was encountered when traversing through the sorted array"
            if (dupeFlag)
            {
                if (checkID == 1)
                {
                    System.out.print("Row " + (checkLoopNum + 1) + " is INVALID: There are duplicates of...  ");
                }
                else if (checkID == 2)
                {
                    System.out.print("Column " + (checkLoopNum + 1) + " is INVALID: There are duplicates of... ");
                }
                else if (checkID == 3)
                {
                    System.out.print("Section " + (checkLoopNum + 1) + " is INVALID: There are duplicates of...  ");
                }

                // Loops through an array that stored the duplicate values found during the previous traversal
                for (int i = 0; i < listCount; i++)
                {
                    // "If we are at the end of the array"
                    if (i == listCount - 1)
                    {
                        System.out.print(dupeRecord[i]);
                    }
                    // "If we NOT at the end of the array"
                    else
                    {
                        System.out.print(dupeRecord[i] + ", ");
                    }
                }
                System.out.println();
                return 1;
            }
            // "If there wasn't any duplicates encountered when traversing through the sorted array"
            else
            {
                if (checkID == 1)
                {
                    System.out.print("Row " + (checkLoopNum + 1) + " is VALID  : There are NO duplicates");
                }
                else if (checkID == 2)
                {
                    System.out.print("Column " + (checkLoopNum + 1) + " is VALID  : There are NO duplicates");
                }
                else if (checkID == 3)
                {
                    System.out.print("Section " + (checkLoopNum + 1) + " is VALID  : There are NO duplicates");
                }
                System.out.println();
            }
            return 0;
        }
    }
}