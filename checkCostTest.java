/*-----THIS IS ONLY A TEST-----*
 * CURRENT VERSION:		0.1
 * 
 * ******************************************
 * VERSION	0.1:							
 * Jason Kelly, 10/5/15						
 * 		added all code and comments			
 * Jason Kelly,	10/6/15						
 * 		added header and commit to github	
 * ******************************************
 * 
 * this is to test the checkCost algorith, currently the board is set to 20x20 (setable with int size above main)to simulate a cluster
 * this is currently a STANDALONE program. to be integrated once project has progressed
 * 
 * FUNCTIONS:
 *		checkCost	-	writes to int boardCost[][], the cost of moving in each point in the cluster
 *		checkSubCost-	calculates cost for each directional vector for distance (sight) of a given point in the cluster. (to be sumed in checkCost)
 *		evalMove	-	Selects the highest cost move and makes that. currently does not support selecting from multiple highest cost moves (and is thus predicatable)
 *		checkStatus	-	checks for win conditions, else it returns 'c' (continue)...currently only returns 'c', no win possible
 *		thinking	-	helps give a visual cue that the AI is thinking (currently runs too fast to notice, to be removed in combined version)
 * 		playerMove	-	get player input for a move
 *		showBoard	-	a crude display of the board
 *	 	clearCost	-	writes every element in boardCost[][] to 0
 * 
 * LINES WITH COMMENT CODE: 
 * 		alpha12		-	comment or decomment to change from (Player vs AI) to (AI vs AI)
 * 		debug45		-	comment or decomment to suppress or show cost values
 * 
 * KNOWN ISSUES:
 * 		in calcCost or calcSubCost	-	The AI determines that going East or South is better than West or North (even though they should be equal)
 * 		IndexOutOfRange	-	AI will sometimes move out of the board (unkown location or condition)(intermitent)
 * 
 * TODO:
 * 		checkStatus	-	add win condition and logic
 * 		evalMove	-	select different highest cost moves
 * 		checkCost	-	plan ahead turns
 * 		integrate with the rest of the project
 * 
 */

import java.util.Scanner;
import java.io.Console;
import java.util.Arrays;
import java.lang.Math;

public class checkCostTest
{
	//cost board
	static int size = 20;
	static int[][] boardCost = new int[size][size];
	
	public static void main(String[] args)
	{
		//build example board
		char[][] board = new char[size][size];
		for(int row=0;row<board.length;row++)
		{
			for(int col=0;col<board.length;col++)
			{
				board[row][col] = '-';
			}
		}
		
		char playerToken = 'x';
		char AIToken = 'o';
		char currentTurn = playerToken;
		
		//holding variables for move and place
		String move = "";
		int row = 0;
		int col = 0;
		
		int turnCount = 0;
		boolean game = true;
		
		//in game loop
		while(game)
		{
				move = "0,0";
				row = 0;
				col = 0;
				showBoard(board);
				//check turn
				if(currentTurn == playerToken)
				{
					//player
						//get coordinate point
						//move = playerMove();							//alpha12
						
						//********************
						//AI VS AI TEST
						checkCost(board,playerToken);					//alpha12
						
						move = evalMove(boardCost);						//alpha12
						//********************
						
						//place
						String[] placement = move.split(",");
						row = Integer.parseInt(placement[0]);
						col = Integer.parseInt(placement[1]);
						if(board[row][col] == '-')
							board[row][col] = playerToken;
				} else {
					//AI
						//check cost
						checkCost(board,AIToken);
						//evaluate cost
						move = evalMove(boardCost);
						//place point
						String[] placement = move.split(",");
						row = Integer.parseInt(placement[0]);
						col = Integer.parseInt(placement[1]);
						board[row][col] = AIToken;
				}			
									
				//check status
				//check for winner?
				if(checkStatus(board) == 'w')
					game = false;


				//switch turns
				if(currentTurn == playerToken)
					currentTurn = AIToken;
				else
					currentTurn = playerToken;
					
				
				//a user input to pause between moves for AI vs AI
				Scanner pause = new Scanner( System.in );				//alpha12
				pause.next();											//alpha12
				
				//a temporary game halter
				turnCount++;
				if(turnCount >= 150)
				{
					game = false;
				}
		}
			//leave board up
			//display winner
		
	}//end main
	
	private static void checkCost(char[][] board, char token)
	{
		//checkCosts
		clearCost();//start turn with empty costs
		char load = '-';//for visual effect
		int costP = 0; //positive (offensive) cost
		int costN = 0; //negitive (defensive) cost
		
		//what player are we?
		char counterToken = ' ';
		if(token == 'o')
			counterToken = 'x';
		else
			counterToken = 'o';
		
		
		System.out.print(" ");
		//for row in board
		for(int row = 0; row < board.length; row++)
		{
			//for col in board
			for(int col = 0; col < board.length; col++)
			{
				//for row_vec -1:1
				for(int row_vec = -1; row_vec < 1; row_vec++)
				{
					//for col_vec -1:1
					for(int col_vec = -1; col_vec < 1; col_vec++)
					{
						//if vectors 0,0: ignore
						if(!(row_vec == 0 && col_vec == 0))
						{
							//if not occupied
							if(board[row][col] == '-')
							{
								try
								{
									costP = calcSubCost(row, col, row_vec, col_vec, 5, token, false, board);
									costN = calcSubCost(row, col, row_vec, col_vec, 5, counterToken, true, board);
									boardCost[row][col] += Math.abs(costP) + Math.abs(costN);
								} catch (IndexOutOfBoundsException e)
								{
									//we hit a wall (unfortunatly this makes the border +sight will have less cost (due to limit of movement)
								}
							}
						}
					}		
				}		
				load = thinking(load);
			}
		}
		/*for future*/
		// plan ahead
		//sum ahead turn costs with current? rework algorithm to determine
		
		
		
		
	}
	private static int calcSubCost(int row, int col, int row_vec, int col_vec, int sight, char token, boolean defense, char[][] board)
	{
		//cost base value
		int cost = 2;
		
		//counterToken lets us known if a blocking move occurs in that line
		char counterToken = ' ';
		if(token == 'o')
			counterToken = 'x';
		else
			counterToken = 'o';
			
		//for i in range sight --- how far are we looking (default 5)
		for(int i = 1; i <= sight; i++)
		{
				//is row + row_vec * i and col + col_vec * i == token?
				if(board[row+(row_vec*i)][col+(col_vec*i)] == token)
					cost = cost*cost;//in attempt to make an 'xxx' move more valuble than two 'xx's
				else if (board[row+(row_vec*i)][col+(col_vec*i)] == counterToken)
					break;
		}			
		if(defense)//check for defensive cost
			cost = -cost;
		return cost;
	}
	private static String evalMove(int[][] board)
	{
		//find highest cost move and makes it (if multiple should pick random)
		//*****************temporarily do found max
		int max = 0;
		int maxrow = 0;
		int maxcol = 0;
		for (int row=0; row < board.length; row++) 
		{
			for (int col=0; col < board.length; col++) 
			{
				if (board[row][col] > max)
				{
					max = board[row][col];
					maxrow = row;
					maxcol = col;
					
				}
				//show ALL cost sorted by coordinates
				//System.out.println("["+(row)+","+(col)+"]\t="+board[row][col]);		//debug45
				
			}
		}
		
		//select random move from possible
		
		
		
		//show horribly laid out grid of the cost with vectors ranging -2:2
		/*
		//debug45
		 
		System.out.print("\n["+(maxrow-2)+","+(maxcol-2)+"]="+board[maxrow-2][maxcol-2]);
		System.out.print("\t["+(maxrow-2)+","+(maxcol-1)+"]="+board[maxrow-2][maxcol-1]);
		System.out.print("\t["+(maxrow-2)+","+(maxcol)+"]="+board[maxrow-2][maxcol]);
		System.out.print("\t["+(maxrow-2)+","+(maxcol+1)+"]="+board[maxrow-2][maxcol+1]);
		System.out.print("\t["+(maxrow-2)+","+(maxcol+2)+"]="+board[maxrow-2][maxcol+2]);

		System.out.print("\n["+(maxrow-1)+","+(maxcol-2)+"]="+board[maxrow-1][maxcol-2]);
		System.out.print("\t["+(maxrow-1)+","+(maxcol-1)+"]="+board[maxrow-1][maxcol-1]);
		System.out.print("\t["+(maxrow-1)+","+(maxcol)+"]="+board[maxrow-1][maxcol]);
		System.out.print("\t["+(maxrow-1)+","+(maxcol+1)+"]="+board[maxrow-1][maxcol+1]);
		System.out.print("\t["+(maxrow-1)+","+(maxcol+2)+"]="+board[maxrow-1][maxcol+2]);

		System.out.print("\n["+(maxrow)+","+(maxcol-2)+"]="+board[maxrow][maxcol-2]);
		System.out.print("\t["+(maxrow)+","+(maxcol-1)+"]="+board[maxrow][maxcol-1]);
		System.out.print("\t["+(maxrow)+","+(maxcol)+"]="+board[maxrow][maxcol]);
		System.out.print("\t["+(maxrow)+","+(maxcol+1)+"]="+board[maxrow][maxcol+1]);
		System.out.print("\t["+(maxrow)+","+(maxcol+2)+"]="+board[maxrow][maxcol+2]);

		System.out.print("\n["+(maxrow+1)+","+(maxcol-2)+"]="+board[maxrow+1][maxcol-2]);
		System.out.print("\t["+(maxrow+1)+","+(maxcol-1)+"]="+board[maxrow+1][maxcol-1]);
		System.out.print("\t["+(maxrow+1)+","+(maxcol)+"]="+board[maxrow+1][maxcol]);
		System.out.print("\t["+(maxrow+1)+","+(maxcol+1)+"]="+board[maxrow+1][maxcol+1]);
		System.out.print("\t["+(maxrow+1)+","+(maxcol+2)+"]="+board[maxrow+1][maxcol+2]);

		System.out.print("\n["+(maxrow+2)+","+(maxcol-2)+"]="+board[maxrow+2][maxcol-2]);
		System.out.print("\t["+(maxrow+2)+","+(maxcol-1)+"]="+board[maxrow+2][maxcol-1]);
		System.out.print("\t["+(maxrow+2)+","+(maxcol)+"]="+board[maxrow+2][maxcol]);
		System.out.print("\t["+(maxrow+2)+","+(maxcol+1)+"]="+board[maxrow+2][maxcol+1]);
		System.out.print("\t["+(maxrow+2)+","+(maxcol+2)+"]="+board[maxrow+2][maxcol+2]);
		 
		//debug45
		*/



		String index = maxrow+","+maxcol;
		return index;
	}
	private static char checkStatus(char[][] board)
	{
		//*****************for time being no check status, let AI have fun
		//check game status
		//status =
			//'w' = win
			//'c' = continue
			//'e' = error, terminate game (unsure if will be implemented)
		return 'c';
	}
	private static char thinking(char load) //for visual afformation of AI processing...should change every block cost calculation
	{
		System.out.print(Character.toString((char)8)+ load);
		switch(load)
		{
			case '/':
				load = '-';break;
			case '-':
				load = '\\';break;
			case '\\':
				load = '|';break;
			case '|':
				load = '/';	break;
		}
		//if this can be watched then something is slowing down. at its current speed this shouldn't be catchable by the human eye
		return load;
	}


	//*****************PLAYER CONTROL
	private static String playerMove()
	{
		//get player input in form of row,col (i.e. 5,5)
		Scanner user_input = new Scanner( System.in );
		String move = "";
		System.out.print("(x,y): ");
		move = user_input.next();
		return move;
	}
	
	private static void showBoard(char[][] board)
	{
		//crude board drawing (and even worse clear screen method)
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		for(int row=0;row<board.length;row++)
		{
			for(int col=0;col<board.length;col++)
			{
				System.out.print(board[row][col]);
			}
			System.out.println();
		}
	}
	private static void clearCost()
	{
		//sets all of boardCost = 0 for recalculating
		for(int row=0;row<boardCost.length;row++)
		{
			for(int col=0;col<boardCost.length;col++)
			{
				boardCost[row][col] = 0;
			}
		}
	}
}
