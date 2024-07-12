/**
 * @author tuckervann
 * Functioning game of checkers for two people to play!
 * Red team begins first and game continues until there is a winner.
 */

package checkers;

import javax.swing.*;
import java.lang.Math;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;


@SuppressWarnings("serial")
public class CheckersGame extends JPanel implements MouseListener
{
	
	public static final int squareLength = 64;
	public static final int squaresInRow = 8;
	public static int[][] pieces = new int[squaresInRow][squaresInRow];
	public static final int empty = 0;
	public static final int red = 1;
	public static final int grey = 2;
	public static final int redKing = 3;
	public static final int greyKing = 4;
	public static final int potentialMove = 5;
	public static int redCount;
	public static int greyCount;
	public static boolean redTurn = true;
	public static int lastJ = 0;
	public int lastI = 0;
	
	/**
	 * Adds mouse clicker and paints the starting board
	 */
	public CheckersGame()
	{
		addMouseListener(this);
		redCount = 12; 
		greyCount = 12;
		setBoardValues();
		repaint();
	}
	
	/**
	 * Remove yellow highlighted squares after a new piece is clicked or move is completed
	 */
	private void removePotentialMove()
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (pieces[i][j] == potentialMove)
				{
					pieces[i][j] = empty;
				}
			}
		}
	}
	
	
	/**
	 * Move checkers piece on 2D array
	 * @param fromY 
	 * Y position on array we are moving from
	 * @param fromX
	 * X position on array we are moving from
	 * @param toY
	 * Y position on array we are now located at
	 * @param toX
	 * X position on array we are now located at
	 * If a piece of the opposite color is jumped, we remove that piece and decrease the count of that respective color
	 * If a grey piece reaches the bottom of the board or a red piece reaches the top, they become a king.
	 */
	public void movePiece(int fromY, int fromX, int toY, int toX)
	{
		pieces[toY][toX] = pieces[fromY][fromX];
		pieces[fromY][fromX] = empty;
		if (Math.abs((fromY - toY)) > 1)
		{
			int a = (toY - fromY)/(Math.abs(toY - fromY));
			int b = (toX - fromX)/(Math.abs(toX - fromX));
			pieces[fromY + a][fromX + b] = empty;
			if (redTurn)
			{
				redCount--;
			}
			else
			{
				greyCount--;
			}
		}
		if (pieces[toY][toX] == red && toY == 0)
		{
			pieces[toY][toX] = redKing;
		}
		if (pieces[toY][toX] == grey && toY == 7)
		{
			pieces[toY][toX] = greyKing;
		}
	}
	
	/**
	 * Initialize 2D array setting the board for the start of the game.
	 */
	public void setBoardValues()
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (((i % 2 == 0) && (j % 2 == 1) || ((i % 2 == 1) && (j % 2 == 0))) && (i < 3))
				{
					pieces[i][j] = grey;
				}
				else if (((i % 2 == 1) && (j % 2 == 0) || ((i % 2 == 0) && (j % 2 == 1))) && (i > 4))
				{
					pieces[i][j] = red;
				}
				else
				{
					pieces[i][j] = empty;
				}
			}
		}
	}
	
	/**
	 * Highlights where a piece can move. Starts with setting base moves for a generic red piece when they can move up.
	 * Kings are included in the if statements as they can move up and down. We also check values of i and j to avoid IndexOutOfBounds.
	 * After checking the up moves we check if we can jump any pieces for red pieces or kings.
	 * We then do the opposite for grey pieces moving down and have grey kings and red kings be able to move down as well and jump any pieces.
	 * @param i
	 * Y position on 2D array
	 * @param j
	 * X position on 2D array
	 */
	public void setPotentialMoves(int i, int j)
	{
		if ((pieces[i][j] == red && redTurn) || (pieces[i][j] == redKing && redTurn) || (pieces[i][j] == greyKing && !redTurn))
		{
			if ((j != 0 && i != 0) && (pieces[i - 1][j - 1] == empty))
			{
				pieces[i - 1][j - 1] = potentialMove;
			}
			if ((j != 7 && i != 0) && (pieces[i - 1][j + 1] == empty))
			{
				pieces[i - 1][j + 1] = potentialMove;
			}
			if ((pieces[i][j] == red)|| (pieces[i][j] == redKing))
			{
				if ((j != 0 && i != 0) && (pieces[i - 1][j - 1] == grey) && ((j > 1 && i > 1) && (pieces[i - 2][j - 2]) == empty))
				{
					pieces[i - 2][j - 2] = potentialMove;
				}
				if ((j != 7 && i != 0) && (pieces[i - 1][j + 1] == grey) && ((i > 1 && j < 6) && (pieces[i - 2][j + 2]) == empty))
				{
					pieces[i - 2][j + 2] = potentialMove;
				}
				if ((j != 0 && i != 0) && (pieces[i - 1][j - 1] == greyKing) && ((j > 1 && i > 1) && (pieces[i - 2][j - 2]) == empty))
				{
					pieces[i - 2][j - 2] = potentialMove;
				}
				if ((j != 7 && i != 0) && (pieces[i - 1][j + 1] == greyKing) && ((i > 1 && j < 6) && (pieces[i - 2][j + 2]) == empty))
				{
					pieces[i - 2][j + 2] = potentialMove;
				}
			}
			if (pieces[i][j] == greyKing)
			{
				if ((j != 0 && i != 0) && (pieces[i - 1][j - 1] == red) && ((j > 1 && i > 1) && (pieces[i - 2][j - 2]) == empty))
				{
					pieces[i - 2][j - 2] = potentialMove;
				}
				if ((j != 7 && i != 0) && (pieces[i - 1][j + 1] == red) && ((i > 1 && j < 6) && (pieces[i - 2][j + 2]) == empty))
				{
					pieces[i - 2][j + 2] = potentialMove;
				}
				if ((j != 0 && i != 0) && (pieces[i - 1][j - 1] == redKing) && ((j > 1 && i > 1) && (pieces[i - 2][j - 2]) == empty))
				{
					pieces[i - 2][j - 2] = potentialMove;
				}
				if ((j != 7 && i != 0) && (pieces[i - 1][j + 1] == redKing) && ((i > 1 && j < 6) && (pieces[i - 2][j + 2]) == empty))
				{
					pieces[i - 2][j + 2] = potentialMove;
				}
			}
		}
		if ((pieces[i][j] == grey && !redTurn) || (pieces[i][j] == greyKing && !redTurn) || (pieces[i][j] == redKing && redTurn))
		{
			if ((j != 0 && i != 7) && (pieces[i + 1][j - 1] == empty))
			{
				pieces[i + 1][j - 1] = potentialMove;
			}
			if ((j != 7 && i != 7) && (pieces[i + 1][j + 1] == empty))
			{
				pieces[i + 1][j + 1] = potentialMove;
			}
			if ((pieces[i][j] == grey) || (pieces[i][j] == greyKing))
			{
				if ((j != 0 && i != 7) && (pieces[i + 1][j - 1] == red) && ((j > 1 && i < 6) && (pieces[i + 2][j - 2]) == empty))
				{
					pieces[i + 2][j - 2] = potentialMove;
				}
				if ((j != 7 && i != 7) && (pieces[i + 1][j + 1] == red) && ((i < 6 && j < 6) && (pieces[i + 2][j + 2]) == empty))
				{
					pieces[i + 2][j + 2] = potentialMove;
				}
				if ((j != 0 && i != 7) && (pieces[i + 1][j - 1] == redKing) && ((j > 1 && i < 6) && (pieces[i + 2][j - 2]) == empty))
				{
					pieces[i + 2][j - 2] = potentialMove;
				}
				if ((j != 7 && i != 7) && (pieces[i + 1][j + 1] == redKing) && ((i < 6 && j < 6) && (pieces[i + 2][j + 2]) == empty))
				{
					pieces[i + 2][j + 2] = potentialMove;
				}
			}
			if (pieces[i][j] == redKing)
			{
				if ((j != 0 && i != 7) && (pieces[i + 1][j - 1] == grey) && ((j > 1 && i < 6) && (pieces[i + 2][j - 2]) == empty))
				{
					pieces[i + 2][j - 2] = potentialMove;
				}
				if ((j != 7 && i != 7) && (pieces[i + 1][j + 1] == grey) && ((i < 6 && j < 6) && (pieces[i + 2][j + 2]) == empty))
				{
					pieces[i + 2][j + 2] = potentialMove;
				}
				if ((j != 0 && i != 7) && (pieces[i + 1][j - 1] == greyKing) && ((j > 1 && i < 6) && (pieces[i + 2][j - 2]) == empty))
				{
					pieces[i + 2][j - 2] = potentialMove;
				}
				if ((j != 7 && i != 7) && (pieces[i + 1][j + 1] == greyKing) && ((i < 6 && j < 6) && (pieces[i + 2][j + 2]) == empty))
				{
					pieces[i + 2][j + 2] = potentialMove;
				}
			}
		}
	}
	
	@Override
	/**
	 * Paints the board and shows display of the 2D array.
	 * If the games over it displays a message signaling the winner.
	 * If not, it paints the board and checks the values of the 2D array to see what to paint.
	 * First it paints the checker pattern on the board with a boolean that is swapped between true and false every iteration.
	 * If its a red piece, it paints a red circle and a grey circle if it is a grey piece.
	 * If its a grey king, it paints a black circle within the grey circle and a grey circle within the black circle.
	 * If its a red king, it paints a black circle within the red circle and a red circle within the black circle.
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		if (redCount == 0 || greyCount == 0)
		{
			g.setColor(Color.black);
			if (redCount == 0)
			{
				g.drawString("Red Wins!", squareLength * 4, squareLength * 4);
			}
			else
			{
				g.drawString("Grey Wins!", squareLength * 4, squareLength * 4);
			}
		}
		else
		{
			boolean white = true;
			for (int i = 0; i < 8; i++)
			{
				for (int j = 0; j < 8; j++)
				{
					if (white)
					{
						g.setColor(Color.black);
					}
					else 
					{
						g.setColor(Color.white);
					}
					g.fillRect(j * squareLength, i * squareLength, squareLength, squareLength);
					white = !white;
					if (pieces[i][j] == grey)
					{
						g.setColor(Color.white.darker());
						g.fillOval(j * squareLength, i * squareLength, squareLength, squareLength);
					}
					if (pieces[i][j] == red)
					{
						g.setColor(Color.red);
						g.fillOval(j * squareLength, i * squareLength, squareLength, squareLength);
					}
					if (pieces[i][j] == potentialMove)
					{
						g.setColor(Color.yellow);
						g.fillRect(j * squareLength, i * squareLength, squareLength, squareLength);
					}
					if (pieces[i][j] == redKing || pieces[i][j] == greyKing)
					{
						if (redKing == pieces[i][j])
						{
							g.setColor(Color.red);
						}
						else
						{
							g.setColor(Color.white.darker());
						}
						g.fillOval(j * squareLength, i * squareLength, squareLength, squareLength);
						g.setColor(Color.black);
						g.fillOval(j * 64 + 8, i * 64 + 8, 48, 48);
						if (redKing == pieces[i][j])
						{
							g.setColor(Color.red);
						}
						else
						{
							g.setColor(Color.white.darker());
						}
						g.fillOval(j * 64 + 16, i * 64 + 16, 32, 32);
					}
				}
				white = !white;
			}
		}
	}
	
	/**
	 * Main method, adds JFrame window which adds the checkers game and begins the game
	 * @param args
	 */
	public static void main(String[] args)
	{
		JFrame window = new JFrame();
		window.getContentPane().add(new CheckersGame());
		window.setBounds(10, 10, 512, 512);
		window.setUndecorated(true);
		window.add(new CheckersGame());
		window.setDefaultCloseOperation(3);
		window.setVisible(true);
	}

	@Override
	/**
	 * Locates where the mouse is clicked. If it is on the grid and on a piece, that piece will then highlight the moves it can make.
	 * Does nothing if square is empty or if you click outside the grid. If you click on a yellow square then the piece will move there.
	 */
	public void mouseClicked(MouseEvent e) {
		int xCoordinate = e.getX();
		int yCoordinate = e.getY();
		if (xCoordinate < 512 && yCoordinate < 512)
		{
			for (int i = 0; i < 8; i++)
			{
				for (int j = 0; j < 8; j++)
				{
					if ((64 * i < yCoordinate) && yCoordinate < (64 * (i + 1))
							&& (64 * j < xCoordinate) && xCoordinate < (64 * (j + 1)) 
							&& pieces[i][j] != empty)
					{
						setPotentialMoves(i, j);
						if (pieces[i][j] == potentialMove)
						{
							removePotentialMove();
							movePiece(lastI, lastJ, i, j);
							redTurn = !redTurn;
						}
						if (pieces[i][j] == grey || pieces[i][j] == greyKing || pieces[i][j] == red || pieces[i][j] == redKing)
						{
							removePotentialMove();
							setPotentialMoves(i, j);
						}
						repaint();
						lastI = i;
						lastJ = j;
					}
				}
			}
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
