package com.sist.client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

public class CalendarSwing extends JFrame {
    private JLabel monthLabel;
    private JPanel calendarPanel;
    private Calendar calendar;

    public CalendarSwing() {
        setTitle("달력 만들기");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        calendar = Calendar.getInstance();

        // 상단 패널 (이전, 현재 월, 다음)
        JPanel topPanel = new JPanel();
        JButton prevButton = new JButton("◀");
        JButton nextButton = new JButton("▶");
        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));

        prevButton.addActionListener(e -> changeMonth(-1));
        nextButton.addActionListener(e -> changeMonth(1));

        topPanel.add(prevButton);
        topPanel.add(monthLabel);
        topPanel.add(nextButton);

        add(topPanel, BorderLayout.NORTH);

        // 달력 패널
        calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(0, 7)); // 7열 (일~토)
        add(calendarPanel, BorderLayout.CENTER);

        drawCalendar();
        setVisible(true);
    }

    private void drawCalendar() {
        calendarPanel.removeAll();

        // 요일 라벨
        String[] week = {"일", "월", "화", "수", "목", "금", "토"};
        for (String day : week) {
            JLabel lbl = new JLabel(day, SwingConstants.CENTER);
            lbl.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            calendarPanel.add(lbl);
        }

        // 이번달 첫날과 마지막날 구하기
        Calendar cal = (Calendar) calendar.clone();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);  // 1=일요일
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 앞 공백
        for (int i = 1; i < firstDayOfWeek; i++) {
            calendarPanel.add(new JLabel(""));
        }

        // 날짜 버튼
        for (int day = 1; day <= lastDay; day++) {
            JButton btn = new JButton(String.valueOf(day));
            btn.addActionListener(e -> {
                String selected = ((JButton) e.getSource()).getText();
                JOptionPane.showMessageDialog(this, selected + "일 선택됨");
            });
            calendarPanel.add(btn);
        }

        // 상단 라벨 업데이트
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 0-based
        monthLabel.setText(year + "년 " + month + "월");

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void changeMonth(int diff) {
        calendar.add(Calendar.MONTH, diff);
        drawCalendar();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalendarSwing::new);
    }
}
