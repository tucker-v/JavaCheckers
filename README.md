Checkers:

My project is a functioning game of Checkers. This project implements MouseListener and extends JPanel. The board is stored in a 2D array that is 8x8. It stores integers and each integer represents what piece is in array slot. To 
display these pieces, I overrode JPanel's paint method and used JFrame to create a window for the project. I looped through the array, painted the checker board pattern, and painted the pieces if the integer in that array slot 
represented a piece. After any changes to the 2D array, the repaint() methodwas called. When running the project, red team begins first. When clicking on the window, mouseClicked() is called upon from implementing MouseListener. 
We then check to see where the mouse was clicked, if it was on a piece, and if it's that piece's turn to move. When a piece is clicked, we update the 2D array to show where that piece can move to. We then call repaint() to show yellow 
square on where you can move. If the mouse is clicked on a new piece, that piece's potential moves are then shown. If a yellow square is clicked, the piece is then moved there. When a piece is moved it is then the other teams turn. 
When a piece is in position to be jumped, it will be shown with a yellow square and if that square is clicked, the piece will be removed. If a piece reaches the end of the board, it becomes a king, having the ability to move fowards
and backwards. The game continues until all pieces are removed. When the game is done the repaint() method will print a message signalling the game is over and printing the winning team.

This project allowed me to learn about ways to code in Java that I hadn't seen in school. This was my first time using JFrame, JPanel, and MouseListener which allowed me to present the game in a way that looked good. Learning JFrame,
JPanel, and MouseListener also seems like very useful things to know how to use. This also allowed me to have a project that shows how I am strong with using 2D arrays.

Things I can potentially add in the future:
- CPU to play against
- Force jumps when multiple jumps can be made in a turn

Sources and Tutorials I used:
https://www.javatpoint.com/java-mouselistener
https://www.youtube.com/watch?v=vO7wHV0HB8w&t=5s
