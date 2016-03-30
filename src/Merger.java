
/**
 * The actual mergesort implementation for the program
 * USING INTS FOR THE TIME BEING/ DON'T KNOW WHAT IT WILL NEED TO BE
 * @author platt, jondef95
 * @version 1
 *
 */
public class Merger {

    private int[] inArray;
    private int[] tempArray;
    private int length;
    /**
     * starts the mergesort class, don't really know what this will need
     */
    public Merger() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * the original input of the array
     * @param input the input array
     */
    public void sort(BufferPool pool)
    {
        int input[] = pool.retrieveRecord();
        inArray = input;
        length = input.length;
        tempArray = new int[length];
        mergeSort(0, length - 1);
    }
    
    /**
     * does the splitting of the values
     * @param bottom is the lower index
     * @param top is the higher index
     */
    private void mergeSort(int bottom, int top)
    {
        if (bottom < top)
        {
            int middle = (bottom + ((top - bottom)/2));
            //sort left hand side (lower side)
            mergeSort(bottom, middle);
            //sort right hand side (higher side)
            mergeSort(middle + 1, top);
            //re-assemble the parts again
            assemble(bottom, middle, top);
        }
    }
    
    private void assemble(int bottom, int middle, int top)
    {
        for (int i = bottom; i <= top; i++)
        {
            tempArray[i] = inArray[i];
        }
        int i = bottom;
        int j = middle + 1;
        int k = top;
        while(i <= middle && j <= top)
        {
            if (tempArray[i] <= tempArray[j])
            {
                inArray[k] = tempArray[i];
                i++;
            }
            else
            {
                inArray[k] = tempArray[j];
                j++;
            }
            k++;
        }
        while(i <= middle)
        {
            inArray[k] = tempArray[i];
            k++;
            i++;
        }
    }

}
