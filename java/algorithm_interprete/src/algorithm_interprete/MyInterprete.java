/**
 * Algorithm Challenge - Interpreter
 * 
 * http://programming-challenges.com/pg.php?page=downloadproblem&probid=110106&format=html
 */

package algorithm_interprete;

public class MyInterprete {
	private static final int MAX_MEMORY = 1000;
	private static final int MAX_REGISTER = 10;
	private static final int MAX_VALUE = 1000;
	
	int[] memory;
	int[] register;
	
	boolean isTerminate = false;
	
	public MyInterprete() {
		init();
	}
	
	private void init() {
		memory = new int[MAX_MEMORY];
		register = new int[MAX_REGISTER];
		
		// init memory
		for(int i =0; i < MAX_MEMORY; i++) {
			memory[i] = 0;
		}
		
		// init register
		for(int i =0; i < MAX_REGISTER; i++) {
			register[i] = 0;
		}
	}
	
	public void loadCommand() {
		// TODO : load data from file.
		
		memory[0] = 299;
		memory[1] = 492;
		memory[2] = 495;
		memory[3] = 399;
		memory[4] = 492;
		memory[5] = 495;
		memory[6] = 399;
		memory[7] = 283;
		memory[8] = 279;
		memory[9] = 689;
		memory[10] = 78;
		memory[11] = 100;
		memory[12] = 000;
		memory[13] = 000;
		memory[14] = 000;
	}
	
	private int parseCommand(int programCounter) {
		// Get current OP command.
		int opCommand = memory[programCounter];
		
		int command = opCommand / 100;
		int arg0 = (opCommand / 10) % 10;
		int arg1 = opCommand % 10;
		
		System.out.println("[" + programCounter + "] " + "command : " + command + ", arg0 : " + arg0 + ", arg1 : " + arg1);
		
		switch(command) {
			case 1:
				if(0 == arg0 && 0 == arg1) {
					isTerminate = true;
				}
				break;
			case 2:
				// 2dn : d �������Ϳ� n ����
				register[arg0] = arg1;
				break;
			case 3:
				// 3dn : d �������Ϳ� n ����
				register[arg0] = (register[arg0] + arg1) % MAX_VALUE;
				break;
			case 4:
				//4dn : d �������Ϳ� n ����
				register[arg0] = (register[arg0] * arg1) % MAX_VALUE;
				break;
			case 5:
				// 5dn : d �������͸� s ���������� ������ ����
				register[arg0] = register[arg1];
				break;
			case 6:
				// 6ds : s ���������� ���� d �������Ϳ� ����
				register[arg0] = (register[arg0] + register[arg1]) % MAX_VALUE;
				break;
			case 7:
				// 7ds : d �������Ϳ� s ���������� ���� ����
				register[arg0] = (register[arg0] * register[arg1]) % MAX_VALUE;
				break;
			case 8:
				// 8da : d �������͸� a �������Ϳ� ����� �ּ��� ���� ����ִ� ������ ����
				register[arg0] = memory[register[arg1]];
				break;
			case 9:
				// 9sa : a �������Ϳ� ����� �ּ��� ���� s ���������� ���� ����
				memory[register[arg1]] = register[arg0];
				break;
			case 0:
				// 0ds : s �������Ϳ� 0 �� ������� ������ d �������Ϳ� �ִ� ��ġ( �޸� �ּ� )�� �̵�
				if(0 != register[arg1]) {
					return register[arg0];
				}
				break;
			default:
				System.err.println("Unknown command!!!");
				break;
		}
		return ++programCounter;
	}
	
	public int execute() {
		int executedCount = 0; // the output, how many execution was done.
		int programCounter = 0; // current execution point
		
		do {
			programCounter = parseCommand(programCounter);
			executedCount++;
		} while(!isTerminate);
		
		//debugRegister();
		
		return executedCount;
	}
	
	public void debugRegister() {
		System.out.print("## Debug Register : ");
		for(int i =0; i < MAX_REGISTER; i++) {
			System.out.print("[" + register[i] + "]");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		MyInterprete interprete = new MyInterprete();
		interprete.loadCommand();
		int result = interprete.execute();
		System.out.println("\nExecuted count : " + result);
	}
}
