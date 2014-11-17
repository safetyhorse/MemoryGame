import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class MemoryGame
{

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frame = new CardFrame();
               frame.setTitle("Who's Poo? Game");
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setVisible(true);
			}
		});

	}

}

class CardFrame extends JFrame
{
	public CardFrame()
	{
		//System.out.println("Running frame constructor");
		MessagePanel mp = new MessagePanel();
		CardPanel cp = new CardPanel(mp);
		setLayout(new BorderLayout());
		add(cp, BorderLayout.CENTER);
		add(new ResetPanel(cp, mp), BorderLayout.SOUTH);
		add(mp, BorderLayout.NORTH);
		pack();
	}

}

class ResetPanel extends JPanel
{
	JButton resetButton = new JButton("Reset");
	CardPanel cp;
	MessagePanel mp;
	
	public ResetPanel(final CardPanel cp, final MessagePanel mp)
	{
		add(resetButton);
		this.cp = cp;
		// draw border to see panel
		//this.setBorder(BorderFactory.createLineBorder(Color.red));
		this.setBackground(Color.WHITE);
		resetButton.setBackground(Color.WHITE);
		//resetButton.setBorder(null);
		//resetButton.setBorderPainted(false);
		resetButton.setFocusPainted(false);
		//Border paddingBorder = BorderFactory.createEmptyBorder(0,10,0,10);
		//resetButton.setBorder(paddingBorder);
		try
		{
			File f = new File("Champagne & Limousines Bold Italic.ttf");
			FileInputStream in = new FileInputStream(f);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT,
					in);
			Font dynamicFont28Pt = dynamicFont.deriveFont(28f);
			resetButton.setFont(dynamicFont28Pt);

		}
		catch(Exception e)
		{
			System.out.println("Loading Font Didn't Work");
		}
		
		resetButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//System.out.println("reset clicked");
				cp.reset();
				mp.setLabelText("Who's Poo Game");
				
			}
		});
	}
	
}

class MessagePanel extends JPanel
{
	JLabel messageLabel = new JLabel();
	
	public MessagePanel()
	{
		add(messageLabel);
		messageLabel.setText("Who's Poo Game");
		//this.setBorder(BorderFactory.createLineBorder(Color.red));
		this.setBackground(Color.WHITE);
		Border paddingBorder = BorderFactory.createEmptyBorder(0,10,0,10);
		messageLabel.setBorder(paddingBorder);
		try
		{
			File f = new File("Champagne & Limousines Bold Italic.ttf");
			FileInputStream in = new FileInputStream(f);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT,
					in);
			Font dynamicFont28Pt = dynamicFont.deriveFont(28f);
			messageLabel.setFont(dynamicFont28Pt);

		}
		catch(Exception e)
		{
			System.out.println("Loading Font Didn't Work");
		}
		
	}
	
	public void setLabelText(String newText)
	{
		messageLabel.setText(newText);
	}
	
	/*@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(800,100);

	}*/
}

class CardPanel extends JPanel implements MouseListener
{
	Card cards[][] = new Card[4][4];
	MessagePanel mp;
	
	public CardPanel(MessagePanel mp)
	{
		this.mp = mp;
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setBackground(Color.WHITE);
		/*// add random generator to shuffle cards
		Random rand = new Random();
		
		//fill panel with the cards
		for(int i = 0; i < cards.length; i++)
		{
			//System.out.println("Making card");
			int randomIndex = rand.nextInt(2)+1;
			cards[i] = new Card(randomIndex);
		}*/
		
		//it would be nicer to be able to generate this array
		//with a loop
		cards[0][0] = new Card(1, "bear");
		cards[0][1] = new Card(1, "bear2");
		cards[0][2] = new Card(2, "bison");
		cards[0][3] = new Card(2, "bison2");
		cards[1][0] = new Card(3, "deer");
		cards[1][1] = new Card(3, "deer2");
		cards[1][2] = new Card(4, "dog");
		cards[1][3] = new Card(4, "dog2");
		cards[2][0] = new Card(5, "elephant");
		cards[2][1] = new Card(5, "elephant2");
		cards[2][2] = new Card(6, "horse");
		cards[2][3] = new Card(6, "horse2");
		cards[3][0] = new Card(7, "lion");
		cards[3][1] = new Card(7, "lion2");
		cards[3][2] = new Card(8, "mouse");
		cards[3][3] = new Card(8, "mouse2");
		
		shuffleCards();
		this.addMouseListener(this);
	}
	
	public void reset()
	{
		//System.out.println("now I'm here in the card panel");
		
		//CardPanel();
				
		/*cards[0] = new Card(1, "A");
		cards[1] = new Card(1, "a-low");
		cards[2] = new Card(2, "B");
		cards[3] = new Card(2, "b-low");*/
		
		shuffleCards();
		//reset cards
		for(int i = 0; i < cards.length; i++)
		{
			for(int j = 0; j < cards.length; j++)
			{
				cards[j][i].setFaceUp(false);
				cards[j][i].setMatched(false);
				cards[j][i].setPlayable(true);
			}
			
		}
		repaint();
		//this.addMouseListener(this);/**/
		
	}

	//shuffle cards
	private void shuffleCards()
	{
		Random rand = new Random();
		for(int row = 0; row < cards.length; row++)
		{
			for(int col = 0; col < cards.length; col++)
			{
				int i1 = rand.nextInt(cards.length);
				int i2 = rand.nextInt(cards.length);
				//System.out.println(randomIndex);
				//set temporary storage for current card
				Card temp = cards[col][row];
				//set current card equal to card at random number
				cards[col][row] = cards[i1][i2];
				//set card at random number equal to temporary stored card
				cards[i1][i2] = temp;
			}
			
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;

		for(int row = 0; row < cards.length; row++)
		{
			for(int col=0; col < cards.length; col++)
			{
				//draw the image for card[i] at x, y, width, height
				g2D.drawImage(cards[row][col].displayImage(),
						col*150,row*150,150,150,null);
				//bump each additional card over by 200 px 
				cards[row][col].setX(col*150);
				cards[row][col].setY(row*150);
			}

			
		}
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(600,600);

	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		int numFaceUp = 0;
		Card card1 = null;
		Card card2 = null;
		int numFaceDown = 0;
		
		mp.setLabelText("Who's Poo Game");
		
		for(int i = 0; i < cards.length; i++)
		{
			for(int j = 0; j < cards.length; j++)
			{
				if(cards[i][j].contains(e.getX(), e.getY()) 
						&& cards[i][j].isPlayable())
				{
					//use either strict or toggle mode
					
					//strict mode - card only turn face up
					cards[i][j].setFaceUp(true);
					
					//toggle mode - card turns face up or down
					//cards[i][j].setFaceUp(!cards[i][j].isFaceUp());
					//System.out.println(cards[i].getMatchId() + " matchId");
					//System.out.println(i + "-" + j);
					//System.out.println(cards[i][j].getX());
					//System.out.println(cards[i][j].getY());
				}
			}
			
		}
		
		//count cards face up after the mouse is pressed
		for(int i = 0; i < cards.length; i++)
		{
			for(int j = 0; j < cards.length; j++)
			{
				if(cards[i][j].isFaceUp() && !cards[i][j].isMatched())
				{
					numFaceUp++;
					if(numFaceUp==1)
					{
						card1 = cards[i][j];
					}
					else if(numFaceUp==2)
					{
						card2 = cards[i][j];
					}
				}
			}
			
			
		}
		
		//count number of cards face down after mouse is pressed
		for(int i = 0; i < cards.length; i++)
		{
			for(int j = 0; j < cards.length; j++)
			{
				if(!cards[i][j].isFaceUp())
				{
					numFaceDown++;
				}
			}
			
		}
		//System.out.println(numFaceUp + " number of cards face up");
		//System.out.println(card1 + " card1");
		//System.out.println(card2 + " card2");
		
		if(numFaceUp==2)
		{
			//if match found identify card as matched
			//and out of play
			if(card1.getMatchId()==card2.getMatchId())
			{
				//System.out.println("Match Found");
				//System.out.println(card1.getMatchId() + " card1");
				//System.out.println(card2.getMatchId() + " card2");
				card1.setMatched(true);
				card1.setPlayable(false);
				card2.setMatched(true);
				card2.setPlayable(false);
				mp.setLabelText("Match Found");
			}
			else
			{
				//System.out.println("No Match");
				mp.setLabelText("No Match");
			}
		}
		
		/*if(numFaceUp<2)
		{
			System.out.println("Not enough cards flipped");
		}*/
		
		//turn all cards in play face down if two cards
		//are already face up and a third one is selected
		if(numFaceUp>2)
		{
			//System.out.println("Too many cards flipped");
			for(int i = 0; i < cards.length; i++)
			{
				for(int j = 0; j < cards.length; j++)
				{
					if(cards[i][j].isPlayable())
				{
					cards[i][j].setFaceUp(false);
				}
				}
				
			}
		}
		
		// if all the cards are face up, you win
		//System.out.println(numFaceDown);
		if(numFaceDown == 0)
		{
			mp.setLabelText("Winner! Click reset to play again.");
		}

		repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
}

class Card
{
	/*change opacity or background color for
	cards that have been matched*/
	
	//declare private attributes
	private Image faceUpImage;
	private Image faceDownImage;
	private boolean isFaceUp;
	private boolean isDealt;
	private boolean isPlayable;
	private int x;
	private int y;
	private int matchId;
	private boolean isMatched;
	
	//defaults
	public Card()
	{
		faceUpImage = new ImageIcon("1.png").getImage();
		faceDownImage = new ImageIcon("back.png").getImage();
		isFaceUp = false;
		isDealt = false;
		isPlayable = false;
		isMatched = false;
		x = 0;
		y = 0;
	}
	
	public Card(int matchId, String imgName)
	{
		x = 0;
		y = 0;
		faceUpImage = new ImageIcon(imgName + ".png").getImage();
		faceDownImage = new ImageIcon("back.png").getImage();
		// show random order of cards facing up
		// switch to isFaceUp = false later
		isFaceUp = false;
		isMatched = false;
		isPlayable = true;
		this.matchId = matchId;
	}
	
	public void setFaceUpImage(Image faceUpImage)
	{
		this.faceUpImage = faceUpImage;
	}
	
	public boolean isFaceUp()
	{
		return isFaceUp;
	}
	
	public void setFaceUp(boolean isFaceUp)
	{
		this.isFaceUp = isFaceUp;
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}

	public Image displayImage()
	{
		if(isFaceUp)
		{
				return faceUpImage;
		}
		else
		{
			return faceDownImage;
		}
	}
	
	public boolean contains(int px, int py)
	{
		Rectangle bounds = new Rectangle(
				x,y,faceUpImage.getWidth(null),
				faceUpImage.getHeight(null));
		return bounds.contains(new Point(px, py));
	}
	
	public int getMatchId()
	{
		return matchId;
	}

	public boolean isMatched() 
	{
		return isMatched;
	}

	public void setMatched(boolean isMatched) 
	{
		this.isMatched = isMatched;
	}
	
	public boolean isPlayable()
	{
		return isPlayable;
	}
	
	public void setPlayable(boolean isPlayable)
	{
		this.isPlayable = isPlayable;
	}

}