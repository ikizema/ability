package com.ability.imaging.processing;

import org.opencv.core.Mat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikizema on 15-09-02.
 */
public class InMemoryDescriptors {
    private List<Integer> listMoviesID = new ArrayList();
    private List<Mat> listMoviesDescriptorsMAT = new ArrayList();
    private String descriptorType = new String();

//    private void LoadMovieDescriptors(String descriptorType) throws SQLException {
//        ConnexionBD connect = new ConnexionBD();
//        connect.connection();
//        String sqlStatementString = "{call GET_DESCRIPTORS(?,?)}";
//        ResultSet rs = null;
//        CallableStatement statement = connect.getConnection().prepareCall(sqlStatementString);
//        statement.setString(1, descriptorType);
//        statement.registerOutParameter(2, -10);
//        statement.executeUpdate();
//        rs = (ResultSet)statement.getObject(2);
//
//        while(rs.next()) {
//            this.listMoviesID.add(Integer.valueOf(rs.getInt("idfilm")));
//            Encoder decoder = new Encoder();
//            decoder.EncoderConstruct(rs.getString("MAT_ENCODED"), rs.getInt("MAT_HEIGHT"), rs.getInt("MAT_WIDTH"), rs.getInt("MAT_CHANNELS"));
//            this.listMoviesDescriptorsMAT.add(decoder.EncoderToMat());
//        }
//
//    }

    public List<Integer> getListMovieID() {
        return this.listMoviesID;
    }

    public List<Mat> getListMovieDescriptorMAT() {
        return this.listMoviesDescriptorsMAT;
    }

    public String getDescriptorType() {
        return this.descriptorType;
    }

    public int getSize() {
        return this.listMoviesID.size();
    }

    public InMemoryDescriptors(String descriptorType) {
        this.descriptorType = descriptorType;

//        try {
//            this.LoadMovieDescriptors(this.descriptorType);
//        } catch (SQLException var3) {
//            var3.printStackTrace();
//        }

    }
}

