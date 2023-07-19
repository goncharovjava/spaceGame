import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Space {
    //������ � ������ �������� ����
    private int width;
    private int height;

    //����������� �������
    private SpaceShip ship;
    //������ ���
    private List<Ufo> ufos = new ArrayList<Ufo>();
    //������ ����
    private List<Bomb> bombs = new ArrayList<Bomb>();
    //������ �����
    private List<Rocket> rockets = new ArrayList<Rocket>();

    public Space(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * �������� ���� ���������.
     * ��� ���������� ��� ������ ��������
     */
    public void run() {
        //������� ����� ��� ���������.
        Canvas canvas = new Canvas(width, height);

        //������� ������ "����������� �� �����������" � �������� ���.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //���� ��������, ���� ������� ���
        while (ship.isAlive()) {
            //"�����������" �������� ������� � ������� ������?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //���� "������� �����" - �������� ������� �����
                System.out.print(event.getKeyCode());
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    ship.moveLeft();
                    //���� "������� ������" - �������� ������� ������
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    ship.moveRight();
                    //���� "������" - ��������
                else if (event.getKeyCode() == KeyEvent.VK_SPACE)
                    ship.fire();
            }

            //������� ��� ������� ����
            moveAllItems();

            //��������� ������������
            checkBombs();
            checkRockets();
            //������� ������� ������� �� �������
            removeDead();

            //������� ��� (1 ��� � 10 �����)
            createUfo();

            //������������ ��� ������� �� �����, � ����� ������� �� �����
            canvas.clear();
            draw(canvas);
            canvas.print();

            //����� 300 �����������
            Space.sleep(300);
        }

        //������� ��������� "Game Over"
        System.out.println("Game Over!");
    }

    /**
     * ������� ��� ������� ����
     */
    public void moveAllItems() {
        getAllItems().forEach(BaseObject :: move);
        //����� �������� ������ ���� ������ �������� � � ������� ������� ����� move().
    }

    /**
     * ����� ���������� ����� ������, ������� �������� ��� ������� ����
     */
    public List<BaseObject> getAllItems() {
        List<BaseObject> o = new ArrayList<>();
        o.addAll(bombs);
        o.addAll(rockets);
        o.addAll(ufos);
        o.add(ship);
        return o;
        //����� ������� ����� ������ � �������� � ���� ��� ������� �������.
    }

    /**
     * ������� ����� ���. 1 ��� �� 10 �������.
     */
    public void createUfo() {
        //��� ����� ������� ����� ���.
    }

    /**
     * ��������� �����.
     * �) ������������ � �������� (����� � ������� �������)
     * �) ������� ���� ���� �������� ���� (����� �������)
     */
    public void checkBombs() {
        //��� ����� ��������� ��� ��������� ������������ ��� ������ �����.
    }

    /**
     * ��������� ������.
     * �) ������������ � ��� (������ � ��� �������)
     * �) ����� ���� ���� �������� ���� (������ �������)
     */
    public void checkRockets() {
        //��� ����� ��������� ��� ��������� ������������ ��� ������ ������.
    }

    /**
     * ������� ������� ������� (�����, ������, ���) �� �������
     */
    public void removeDead() {
        //��� ����� ������� ��� ������� ������� �� ������� (����� ������������ �������)
    }

    /**
     * ��������� ���� �������� ����:
     * �) ��������� ���� ����� �������.
     * �) ������������ ��� ������� �� �����.
     */
    public void draw(Canvas canvas) {
        //��� ����� ���������� ��� ������� ����
    }


    public SpaceShip getShip() {
        return ship;
    }

    public void setShip(SpaceShip ship) {
        this.ship = ship;
    }

    public List<Ufo> getUfos() {
        return ufos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public List<Rocket> getRockets() {
        return rockets;
    }

    public static Space game;

    public static void main(String[] args) throws Exception {
        game = new Space(20, 20);
        game.setShip(new SpaceShip(10, 18));
        game.run();
    }

    /**
     * ����� ������ ����� ������� delay �����������.
     */
    public static void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ignored) {
        }
    }
}
