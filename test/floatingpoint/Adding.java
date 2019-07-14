package floatingpoint;

public class Adding {

	public static void main(String[] args) {
//		double v = 0.1;
//		while (v != 1.0) {
//			System.out.println(v);
//			v = v + 0.1;
//		}
//
//		System.out.print("v: ");
//		System.out.println(v);

//		double x = 0.1;
//		double sum = x + x + x;
//		System.out.print("A: ");
//		System.out.println(sum == 0.3);
//		System.out.print("B: ");
//		System.out.println(sum);

		double bigger = 1.0;
		double s = 1.0e-6; // 0.000001
		double sum1 = s + s + s + s + s + s + s + s + s + s + bigger;
		double sum2 = bigger + s + s + s + s + s + s + s + s + s + s;
		System.out.print("C: ");
		System.out.println(sum1 == sum2);
		System.out.print("D: ");
		System.out.println(sum1);
		System.out.print("E: ");
		System.out.println(sum2);
	}
}