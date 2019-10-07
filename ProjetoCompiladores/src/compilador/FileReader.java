package compilador;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader 
{
    private int mCharacter;
    InputStreamReader mISReader;

    public FileReader(String path) throws IOException 
    {
        FileInputStream file = new FileInputStream(path);
        mISReader = new InputStreamReader(file);
    }

    public int leituraCaracter()
    {
        try
        {
            if(mCharacter != -1)
            {
                mCharacter = mISReader.read(); 
                return mCharacter;
            }
            else
            {
            	mISReader.close();
                return mCharacter;
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage()); 
        }
        return 0;
    }
}

