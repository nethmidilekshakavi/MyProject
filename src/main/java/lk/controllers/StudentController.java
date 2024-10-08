package lk.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.DAO.StudentDao;
import lk.DaoFactory;
import lk.Entity.Student;
import lk.Repo.StudentRepo;
import lk.model.StudentModel;
import lk.model.TM.studentTM;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    StudentDao studentDao = (StudentDao) DaoFactory.getDaoFactory().getDAO(DaoFactory.DAOTypes.STUDENT);

    @FXML
    private TextField addresstxt;

    @FXML
    private TableColumn<studentTM, String> coladdress;

    @FXML
    private TableColumn<studentTM,Integer> colid;

    @FXML
    private TableColumn<studentTM, String> colname;

    @FXML
    private TableColumn<studentTM, Integer> coltele;

    @FXML
    private Button delete;

    @FXML
    private TextField idtxt;

    @FXML
    private TextField nametxtx;

    @FXML
    private TableView<studentTM> tableStudent;

    @FXML
    private TextField teletxt;

    @FXML
    private Button update;

    @FXML
    void studentdelete(ActionEvent event) {

        int id = Integer.parseInt(idtxt.getText());

        boolean d = false;
        try {
            d = studentDao.delete(String.valueOf(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (d){

           new Alert(Alert.AlertType.CONFIRMATION,"Student Deleted").show();
       }
       else {
           new Alert(Alert.AlertType.ERROR,"Student not Deleted").show();
       }

    }

    @FXML
    void studentsave(ActionEvent event) throws SQLException{

        int id = Integer.parseInt(idtxt.getText());
        String nam = nametxtx.getText();
        String address = addresstxt.getText();
        int tele = Integer.parseInt(teletxt.getText());

        Student student = new Student(id,nam,address,tele);
        boolean s = false;
        try {
            s = studentDao.save(student);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (s){
            new Alert(Alert.AlertType.CONFIRMATION,"Student Save Success").show();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Student not save").show();

        }
        }



    @FXML
    void studentupdate(ActionEvent event) {

        int id = Integer.parseInt(idtxt.getText());
        String name = nametxtx.getText();
        String address = addresstxt.getText();
        int phone = Integer.parseInt(teletxt.getText());

        Student student = new Student(id,name,address,phone);
        boolean u = false;
        try {
            u = studentDao.update(student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (u){

            new Alert(Alert.AlertType.CONFIRMATION,"Student Updated").show();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Student not Updated").show();
        }


    }

    public void loadallvalues() throws SQLException {

        ArrayList<Student> allstudent = null;
        try {
            allstudent = studentDao.getAll();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ObservableList<studentTM>observableList= FXCollections.observableArrayList();

        for (int i = 0; i<allstudent.size(); i++){
           /* String id = String.valueOf(allstudent.get(i).getSid());
            String tele = String.valueOf(allstudent.get(i).getPhone());*/

            studentTM studentTM = new studentTM(allstudent.get(i).getSid(),allstudent.get(i).getName(),allstudent.get(i).getAddress(),allstudent.get(i).getPhone());
            observableList.add(studentTM);
            tableStudent.setItems(observableList);

        }


    }

    public void setValues(){
        colid.setCellValueFactory(new PropertyValueFactory<>("SID"));
        colname.setCellValueFactory(new PropertyValueFactory<>("NAME"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("ADDRESS"));
        coltele.setCellValueFactory(new PropertyValueFactory<>("phone"));

    }




    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setValues();
        loadallvalues();
    }
}
