package JavaSE.lambda;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @Author: clf
 * @Date: 19-3-8
 * @Description:
 * 使用lambda进行事件监听
 */
public class ListenerHandle {
    public static void main(String[] args) {
        JButton show = new JButton("show");
        show.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Event handling without lambda expression is boring");
            }
        });
        show.addActionListener(actionEvent -> {
            System.out.println("Lambda express is better");
        });

    }
}
