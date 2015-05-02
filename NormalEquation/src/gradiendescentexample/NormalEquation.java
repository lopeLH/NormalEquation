/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradiendescentexample;

import Jama.Matrix;

/**
 *
 * @author lope
 */
public class NormalEquation {
    
    Matrix O;
    Matrix X,Y;
    double [][]data;
    double []out;
    double []omega;
    
    public NormalEquation(double[][] data, double[] out){
        
        // Add X0 = 1 to each vector of features
        double[][] dataWithXo = new double[data.length][data[0].length+1];
        for (int i = 0; i < dataWithXo.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                dataWithXo[i][j] = data[i][j];
            }
            dataWithXo[i][data[0].length] = 1; 
        }
        this.X = new Matrix(dataWithXo);
        this.Y = (new Matrix(new double[][] {out})).transpose();
        Matrix Xt = X.transpose();
        this.O = (((Xt.times(X)).inverse()).times(Xt)).times(Y);
                
        omega = new double[O.getRowDimension()];
        for (int i = 0; i < omega.length; i++) {
            omega[i] = O.get(i, 0);            
        }
        
        this.data = dataWithXo;
        this.out = out;
    }
    
    public float meanSquareError(){
        float sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum +=  Math.pow(this.computeEstimateWithXo(data[i])-out[i],2);
        }
        sum = sum/data.length;
        return sum;
    };
    
        
    public double computeEstimate(double []x){
        double out = 0;
        double[] xwithXo = new double[omega.length];
        for (int i = 0; i < omega.length-1; i++) {
            xwithXo[i] = x[i];
        }
        xwithXo[omega.length-1] = 1;
                
        for (int i = 0; i < omega.length; i++) {
            out += omega[i]*xwithXo[i];
        }
        return out;
    }
    
    private double computeEstimateWithXo(double []xwithXo){
        double out = 0;
               
        for (int i = 0; i < xwithXo.length; i++) {
            out += omega[i]*xwithXo[i];
        }
        return out;
    }

}
