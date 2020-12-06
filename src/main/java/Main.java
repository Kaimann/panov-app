import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.stream.IntStream;

public class Main extends JFrame {

    private JTextField textField;
    private int number;
    private int[] array;
    private boolean flag = true;
    private JButton[] arrButtons;
    private JPanel jPanel1;

    /* Точка входа */

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Main frame = new Main();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /* Конструктор */

    public Main(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setBounds(screenWidth/2-507, screenHeight/2-300, 1014, 600);
        doIntroScreen();
    }

    /* Реализация первой страницы */

    private void doIntroScreen() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setVisible(true);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        textField.setBounds(430, 220, 150, 40);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel label = new JLabel("How many numbers to display?");
        label.setBackground(Color.BLACK);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Tahoma", Font.PLAIN, 21));
        label.setBounds(350, 155, 300, 68);
        contentPane.add(label);

        JButton buttonEnter = new JButton("Enter");
        buttonEnter.setFont(new Font("Tahoma", Font.PLAIN, 26));
        buttonEnter.setBackground(Color.BLUE);
        buttonEnter.setForeground(Color.WHITE);
        buttonEnter.setBounds(430, 285, 150, 40);
        buttonEnter.addActionListener(e -> {
            try {
                number = Integer.parseInt(textField.getText());
                if ((number < 1000) && (number > 0)) {

                     doSortScreen();
                     doArray(number);
                     doListButton(array);

                } else {
                    textField.setText("");
                    String message = "Please select a value from 1 to 999.";
                    JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
                }
            } catch (Exception ex) {
                textField.setText("");
                String message = "Please select a value from 1 to 999.";
                JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
            }
        });

        contentPane.add(buttonEnter);
    }

    /* Реализация второй страницы */

    private void doSortScreen(){

        JButton buttonSort = new JButton("Sort");
        buttonSort.setFont(new Font("Tahoma", Font.PLAIN, 22));
        buttonSort.setBounds(50, 80, 110, 30);
        buttonSort.setForeground(Color.WHITE);
        buttonSort.setBackground(Color.GREEN);
        buttonSort.addActionListener(e -> {

            if (flag) {
                quickSort(array,0,array.length-1);
                renameButton(array);
                flag = false;
            } else {
                reverseOrder(array);
                renameButton(array);
                flag = true;
            }
        });

        JButton buttonReset = new JButton("Reset");
        buttonReset.setFont(new Font("Tahoma", Font.PLAIN, 22));
        buttonReset.setBounds( 50,  135, 110, 30);
        buttonReset.setForeground(Color.WHITE);
        buttonReset.setBackground(Color.GREEN);

        buttonReset.addActionListener(e -> {
            doIntroScreen();
            flag = true;
        });


        JPanel contentPane2 = new JPanel(null);
        contentPane2.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane2);
        contentPane2.setLayout(null);

        jPanel1 = new JPanel();
        jPanel1.setLayout(null);
        setVisible(true);

        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(null);
        jPanel2.setBounds(800, 5, 230, 550);
        setVisible(true);

        jPanel2.add(buttonSort);
        jPanel2.add(buttonReset);

        JScrollPane scroll = new JScrollPane(jPanel1);
        scroll.setLocation(5,5);
        scroll.setSize(new Dimension(800, 563));
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        contentPane2.add(scroll);
        contentPane2.add(jPanel2);

    }

    /* Алгоритм Quick Sort */

    private int partition ( int arr[], int left, int right){
        int pivot = arr[left];
        int i = left;
        for (int j = left + 1; j <= right; j++) {
            if (arr[j] > pivot) {
                i = i + 1;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i];
        arr[i] = arr[left];
        arr[left] = temp;

        return i;

    }

    private void quickSort ( int arr[], int left, int right){
        if (left < right) {
            int q = partition(arr, left, right);
            quickSort(arr, left, q);
            quickSort(arr, q + 1, right);
        }
    }

    /* Разворот массива в обратном порядке */

    private void reverseOrder(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int tmp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = tmp;
        }
    }

    /* Создание массива случайных чисел заданной длины */

    private int[] doArray (int number) {
        array = new int[number];
        do {
            for (int i = 0; i < number; i++) {
                array[i] = (int)((Math.random() * 999)+1);
            }
        } while (IntStream.of(array).noneMatch(x -> ((x >= 1) && (x <= 30))));

        return array;
    }

    /* Создание кнопок с числами из массива */

    private void doListButton(int[] array) {
        int x = 50;
        int y = 80;
        int width = 75;
        int height = 30;
        int lenght = array.length;

        arrButtons = new JButton[lenght];

        for (int i = 0; i < lenght; i++) {

            JButton button = new JButton();
                arrButtons[i] = button;
                button.setText(String.valueOf(array[i]));
                button.setFont(new Font(null, Font.PLAIN, 12));
                button.setBackground(Color.BLUE);
                button.setForeground(Color.WHITE);
                button.setBounds(x, y, width, height);

                button.addActionListener(e -> {

                    int n = Integer.parseInt(e.getActionCommand());
                    if ((n > 0) && (n < 31)){

                        doSortScreen();
                        int[] newArr = doArray(n);
                        doListButton(newArr);
                        flag = true;

                    }   else {
                        String message = "Please select a value smaller or equal to 30.";
                        JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
                    }
                });

                jPanel1.add(button);

                if (((i - 9) % 10) == 0) {
                    y = 80;
                    x = x +100;
                } else {
                    y = y + 40;
                }
        }

        final Dimension d = lenght % 10 == 0 ? new Dimension(x, 540) : new Dimension(x + 100, 540);
        jPanel1.setPreferredSize(d);
        jPanel1.revalidate();
        setVisible(true);
    }

    /* Переименование кнопок отсортированными числами */

    private void renameButton(int[] array) {

        int length = array.length;
        int time = 100;

            for (int j = 0; j < length; j++) {

                int finalJ = j;

                final Timer t = new Timer(time, evt -> {
                    try {
                        arrButtons[finalJ].setText(String.valueOf(array[finalJ]));
                    } catch (ArrayIndexOutOfBoundsException e) {
                       // e.printStackTrace();
                    }

                });

                t.setRepeats(false);
                t.start();
                time = time + 200;
        }
    }
}
