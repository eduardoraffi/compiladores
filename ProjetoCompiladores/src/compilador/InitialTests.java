package compilador;

public class InitialTests
{
	public static void main(String[] args) 
	{
		String path = "E:\\Lexico\\";
		try {
			LexicalAnalyzer lexycal = new LexicalAnalyzer(path + "teste5.txt");
			
			for(int i = 0; i< lexycal.vetorDeTokens.size() -1 ; i++)
			System.out.println(lexycal.vetorDeTokens.get(i));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
