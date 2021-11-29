import java.util.ArrayList;
import java.util.Arrays;

final class ResultsCommand {
    private  String first;
    private  String[] second;

    public void SetResult(String first) {
        this.first = first;
    }

    public void setMyArray(String[] newArray)
    {
        this.second = new String[newArray.length];

        for(int i = 0;i<newArray.length;i++)
        {
            this.second[i] = newArray[i];
        }

    }



    public String[] getMyArray()
    {
        String[] temp = new String[second.length];

        for(int i = 0;i<second.length;++i)
        {
            temp[i] = this.second[i];
        }

        return temp;
    }


    public String getFirst() {
        return first;
    }

}

