package gui.table;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class JTableTest extends JFrame implements MouseListener, KeyListener {

	private final String[] labels = { "사원번호", "이름", "성씨", "이메일", "입사일자", "직무" };
	private JTextField[] fields = new JTextField[6];

	private JScrollPane scrolledTable;
	private JTable table;

	private JButton addBtn;
	private JButton delBtn;

	ChatDAO dao = new ChatDAO();

	public JTableTest(String title) {

		this.setLayout(new BorderLayout(10, 10));

		// 위쪽 입력 값 등록.
		JPanel topPanel = new JPanel(new GridLayout(6, 4, 10, 5));

		for (int i = 0; i < 6; i++) {
			topPanel.add(new JLabel(labels[i]));
			fields[i] = new JTextField(30);
			topPanel.add(fields[i]);
		}
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add("North", topPanel); // 가장 위쪽 Panel 설정

		// table 구성.
		String header[] = { "사원번호", "이름", "성씨", "이메일", "입사일자", "직무" };
		DefaultTableModel model = new DefaultTableModel(header, 0); // header추가, 행은 0개 지정

		table = new JTable(model);
		scrolledTable = new JScrollPane(table); // 스크롤 될 수 있도록 JScrollPane 적용
		scrolledTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 너무 붙어있어서 가장자리 띄움(padding)

		// 데이터 로딩.
		List<Employee> list = dao.getList();
		String[] record = new String[6];
		for (Employee emp : list) {
			record[0] = String.valueOf(emp.getEmployeeId());
			record[1] = emp.getFirstName();
			record[2] = emp.getLastName();
			record[3] = emp.getEmail();
			record[4] = emp.getHireDate();
			record[5] = emp.getJobId();
			model.addRow(record);
		}

		this.add("Center", scrolledTable); // 가운데에 JTable 추가

		JPanel rightPanel = new JPanel(new GridLayout(5, 1, 10, 10));

		addBtn = new JButton("레코드 추가");
		delBtn = new JButton("레코드 삭제");

		rightPanel.add(addBtn);
		rightPanel.add(delBtn);
		rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.add("East", rightPanel); // 오른쪽에 버튼들 추가

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(620, 400);
		this.setLocationRelativeTo(null); // 창 가운데 위치
		this.setVisible(true);

		// 이벤트 추가
		addBtn.addMouseListener(this); // 추가 처리
		delBtn.addMouseListener(this); // 삭제 처리
		for (int i = 0; i < 6; i++) // 엔터 처리
			fields[i].addKeyListener(this);
		table.addMouseListener(this); // 셀 읽기 처리

	}

	private boolean isInvalidInput(String input) {
		return input == null || input.length() == 0;
	}

	public void removeRecord(int index) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (index < 0) {
			if (table.getRowCount() == 0)// 비어있는 테이블이면
				return;
			index = 0;
		}
		model.removeRow(index);
	}

	public void addRecord() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		String[] record = new String[6];
		for (int i = 0; i < 6; i++) {
			if (isInvalidInput(fields[i].getText())) {
				System.out.println("Invalid Input");
				return;
			}
			record[i] = fields[i].getText();
		}
		Employee emp = new Employee(Integer.parseInt(record[0]), record[1], record[2], record[3], record[4], record[5]);
		if (!dao.addEmp(emp)) {
			JOptionPane.showMessageDialog(null, "저장 중 에러", "경고창", JOptionPane.WARNING_MESSAGE);
			return;
		}
		model.addRow(record);

		// 모든 TextField 비우기
		for (int i = 0; i < 6; i++)
			fields[i].setText("");

		fields[0].requestFocus();
	}

	public void printCell(int row, int col) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		System.out.println(model.getValueAt(row, col));
		System.out.println(model.getValueAt(row, 0));

		int selectedId = Integer.parseInt((String) model.getValueAt(row, 0));
		Employee emp = dao.getEmpl(selectedId);

		showDetail(emp);

	}

	public void showDetail(Employee emp) {
		fields[0].setText(String.valueOf(emp.getEmployeeId()));
		fields[1].setText(emp.getFirstName());
		fields[2].setText(emp.getLastName());
		fields[3].setText(emp.getEmail());
		fields[4].setText(emp.getHireDate());
		fields[5].setText(emp.getJobId());

	}

	// MouseListener Overrides
	@Override
	public void mouseClicked(MouseEvent e) {
		Object src = e.getSource();
		if (src == addBtn)
			addRecord();

		if (src == delBtn) {
			int selected = table.getSelectedRow();
			removeRecord(selected);
		}

		if (src == table) {
			int row = table.getSelectedRow();
			int col = table.getSelectedColumn();
			printCell(row, col);
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mousePressed");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("mouseReleased");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("mouseEntered");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("mouseExited");
	}

	// KeyListener Overrides
	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("keyTyped");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("keyPressed");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ENTER) {
			addRecord();
		}
	}

	// main method start.
	public static void main(String[] args) {
		new JTableTest("test");
	}
}