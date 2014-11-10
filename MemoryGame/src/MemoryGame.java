import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MemoryGame
{

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frame = new CardFrame();
               frame.setTitle("Memory Card Game");
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
		add(new CardPanel());
		pack();
	}
}

class CardPanel extends JPanel implements MouseListener
{
	Card cards[] = new Card[4];
	
	public CardPanel()
	{
		// add random generator to shuffle cards
		Random rand = new Random();
		
		//fill panel with the cards
		for(int i = 0; i < cards.length; i++)
		{
			//System.out.println("Making card");
			int randomIndex = rand.nextInt(2)+1;
			cards[i] = new Card(randomIndex);
		}
		this.addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2D = (Graphics2D)g;

		for(int i = 0; i < cards.length; i++)
		{

			//draw the image for card[i] at x, y, width, height
			g2D.drawImage(cards[i].displayImage(),
					i*200,200,200,200,null);
			//bump each additional card over by 125 px 
			cards[i].setX(i*200);
			cards[i].setY(200);
		}
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(800,800);

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
		
		for(int i = 0; i < cards.length; i++)
		{
			if(cards[i].contains(e.getX(), e.getY()))
			{
				//cards[i].setFaceUp(true);
				//toggle to opposite of if card is up or down
				cards[i].setFaceUp(!cards[i].isFaceUp());
				//System.out.println(cards[i].getMatchId() + " matchId");
			}
		}
		
		//count cards face up after the mouse is pressed
		for(int i = 0; i < cards.length; i++)
		{
			if(cards[i].isFaceUp() && !cards[i].isMatched())
			{
				numFaceUp++;
				if(numFaceUp==1)
				{
					card1 = cards[i];
				}
				else if(numFaceUp==2)
				{
					card2 = cards[i];
				}
			}
			
		}
		System.out.println(numFaceUp + " number of cards face up");
		//System.out.println(card1 + " card1");
		//System.out.println(card2 + " card2");
		if(numFaceUp==2)
		{
			if(card1.getMatchId()==card2.getMatchId())
			{
				System.out.println("Match Found");
				System.out.println(card1.getMatchId() + " card1");
				System.out.println(card2.getMatchId() + " card2");
				//card1.setMatched(true);
				//card2.setMatched(true);
			}
			else
			{
				System.out.println("No Match");
			}
		}
		
		if(numFaceUp<2)
		{
			System.out.println("Not enough cards flipped");
		}
		if(numFaceUp>2)
		{
			System.out.println("Too many cards flipped");
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
		faceUpImage = new ImageIcon("1.jpg").getImage();
		faceDownImage = new ImageIcon("back.png").getImage();
		isFaceUp = false;
		isDealt = false;
		isPlayable = false;
		isMatched = false;
		x = 0;
		y = 0;
	}
	
	public Card(int matchId)
	{
		x = 0;
		y = 0;
		faceUpImage = new ImageIcon(matchId + ".jpg").getImage();
		faceDownImage = new ImageIcon("back.png").getImage();
		// show random order of cards facing up
		// switch to isFaceUp = false later
		isFaceUp = false;
		isMatched = false;
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

	public boolean isMatched() {
		return isMatched;
	}

	public void setMatched(boolean isMatched) {
		this.isMatched = isMatched;
	}
}