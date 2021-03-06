import java.util.*;
public class Encryptor
{
    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;

    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;

    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;

    /** Constructor*/
    public Encryptor(int r, int c)
    {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }

    public String[][] getLetterBlock()
    {
        return letterBlock;
    }

    /** Places a string into letterBlock in row-major order.
     *
     *   @param str  the string to be processed
     *
     *   Postcondition:
     *     if str.length() < numRows * numCols, "A" in each unfilled cell
     *     if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str)
    {
        int count = 0;
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                if (count < str.length()){
                    letterBlock[i][j] = str.substring(count, count + 1);
                    count++;
                } else {
                    letterBlock[i][j] = "A";
                }
            }
        }
    }

    /** Extracts encrypted string from letterBlock in column-major order.
     *
     *   Precondition: letterBlock has been filled
     *
     *   @return the encrypted string from letterBlock
     */
    public String encryptBlock()
    {
        String str = "";
        for (int i = 0; i < numCols; i++){
            for (int j = 0; j < numRows; j++){
                str += letterBlock[j][i];
            }
        }
        return str;
    }

    /** Encrypts a message.
     *
     *  @param message the string to be encrypted
     *
     *  @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message)
    {
        String encrypted = "";
        if (message.equals("")){
            return message;
        }
        int size = numRows * numCols;
        String str = message;
        while (!str.equals("")){
            if (str.length() < size){
                String part = str.substring(0, str.length());
                fillBlock(part);
                encrypted += encryptBlock();
                str = "";
            } else {
                String part = str.substring(0, size);
                fillBlock(part);
                encrypted += encryptBlock();
                str = str.substring(size);
            }
        }
        return encrypted;
    }

    /**  Decrypts an encrypted message. All filler 'A's that may have been
     *   added during encryption will be removed, so this assumes that the
     *   original message (BEFORE it was encrypted) did NOT end in a capital A!
     *
     *   NOTE! When you are decrypting an encrypted message,
     *         be sure that you have initialized your Encryptor object
     *         with the same row/column used to encrypted the message! (i.e.
     *         the ???encryption key??? that is necessary for successful decryption)
     *         This is outlined in the precondition below.
     *
     *   Precondition: the Encryptor object being used for decryption has been
     *                 initialized with the same number of rows and columns
     *                 as was used for the Encryptor object used for encryption.
     *
     *   @param encryptedMessage  the encrypted message to decrypt
     *
     *   @return  the decrypted, original message (which had been encrypted)
     *
     *   TIP: You are encouraged to create other helper methods as you see fit
     *        (e.g. a method to decrypt each section of the decrypted message,
     *         similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage)
    {
        String decrypted = "";
        int count = 0;
        for (int x = 0; x < encryptedMessage.length()/(numCols * numRows); x++){
            for (int i = 0; i < letterBlock[0].length; i++){
                for (int j = 0; j < letterBlock.length; j++){
                    letterBlock[j][i] = encryptedMessage.substring(count, count + 1);
                    count++;
                }
            }
            decrypted += decryptBlock();
        }
        while (decrypted.substring(decrypted.length() - 1).equals("A")){
            decrypted = decrypted.substring(0, decrypted.length() -1);
        }
        return decrypted;
    }

    private String decryptBlock()
    {
        String str = "";
        for (int i = 0; i < numRows; i++)
        {
            for (int j = 0; j < numCols; j++)
            {
                str += letterBlock[i][j];
            }
        }
        return str;
    }
}