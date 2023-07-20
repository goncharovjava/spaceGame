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
                    //���� "������" - ��������� �����
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
        for (BaseObject object : getAllItems()) {
            object.move();
        }
    }

    /**
     * ����� ���������� ����� ������, ������� �������� ��� ������� ����
     */
    public List<BaseObject> getAllItems() {
        ArrayList<BaseObject> list = new ArrayList<>(ufos);
        list.add(ship);
        list.addAll(bombs);
        list.addAll(rockets);
        return list;
    }

    /**
     * ������� ����� ���. 1 ��� �� 10 �������.
     */
    public void createUfo() {
        if (ufos.size() > 0) return;

        int random10 = (int) (Math.random() * 10);
        if (random10 == 0) {
            double x = Math.random() * width;
            double y = Math.random() * height / 2;
            ufos.add(new Ufo(x, y));
        }
    }

    /**
     * ��������� �����.
     * �) ������������ � �������� (����� � ������� �������)
     * �) ������� ���� ���� �������� ���� (����� �������)
     */
    public void checkBombs() {
        for (Bomb bomb : bombs) {
            if (ship.isIntersect(bomb)) {
                ship.die();
                bomb.die();
            }

            if (bomb.getY() >= height)
                bomb.die();
        }
    }

    /**
     * ��������� ������.
     * �) ������������ � ��� (������ � ��� �������)
     * �) ����� ���� ���� �������� ���� (������ �������)
     */
    public void checkRockets() {
        for (Rocket rocket : rockets) {
            for (Ufo ufo : ufos) {
                if (ufo.isIntersect(rocket)) {
                    ufo.die();
                    rocket.die();
                }
            }

            if (rocket.getY() <= 0)
                rocket.die();
        }
    }

    /**
     * ������� �������� ������� (�����, ������, ���) �� �������
     */
    public void removeDead() {
        for (BaseObject object : new ArrayList<BaseObject>(bombs)) {
            if (!object.isAlive())
                bombs.remove(object);
        }

        for (BaseObject object : new ArrayList<BaseObject>(rockets)) {
            if (!object.isAlive())
                rockets.remove(object);
        }

        for (BaseObject object : new ArrayList<BaseObject>(ufos)) {
            if (!object.isAlive())
                ufos.remove(object);
        }
    }

    /**
     * ��������� ���� �������� ����:
     * �) ��������� ���� ����� ���������.
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
