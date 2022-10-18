package co.yedam.collect;

import java.util.Scanner;

public class TypingSpeedGame {

	public static void main(String[] args) {

		String orign = "The new Madrid region will deliver hundreds of OCI services to help organizations";
		String[] target = orign.split(" ");

		Scanner scn = new Scanner(System.in);
		boolean done = false;
		long startTime = System.currentTimeMillis();

		while (true) {

			done = true; // 빈 값이 있는지를 체크하기 위한 용도.
			for (String str : target) {
				if (str != null) {
					System.out.printf("%s ", str);
					done = false;
				}
			}
			if (done) {
				break;
			}

			System.out.print("\n입력>> ");
			String word = scn.nextLine();

			// 같은 단어가 있는 지 체크해서 있으면 삭제.
			for (int i = 0; i < target.length; i++) {
				if (target[i] != null && target[i].equals(word)) {
					target[i] = null;
				}
			}

		} // end of while

		long endTime = System.currentTimeMillis();
		long duringTime = endTime - startTime;

		long min = duringTime / (60 * 1000);
		long sec = duringTime % (60 * 1000) / 1000;

		System.out.println("걸린시간은 " + min + "분 " + sec + "초");
		scn.close();
	}
}
