package nsit.com.canteenapp.CommonUtils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import nsit.com.canteenapp.DTO.MyCartScreenDTO;

/**
 * Created by starhawk on 04/08/18.
 */

public class MyCartOperations {

    private Context context;

    public MyCartOperations(Context context){
        this.context = context;
    }

    public void writeToFile(ArrayList<MyCartScreenDTO> myCartScreenDTOArrayList) throws IOException {
        File file = CommonUtilsClass.openFile(context,"MyCart.ser");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(myCartScreenDTOArrayList);
        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public ArrayList<MyCartScreenDTO> readFromFile() throws IOException, ClassNotFoundException {
        File file = CommonUtilsClass.openFile(context,"MyCart.ser");
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ArrayList<MyCartScreenDTO> myCartScreenDTOArrayList = (ArrayList<MyCartScreenDTO>) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        return myCartScreenDTOArrayList;
    }

}
