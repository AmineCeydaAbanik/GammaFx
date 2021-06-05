/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gammafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Amine Ceyda Abanik
 */
public class Gammafx extends Application {
    Label lblgiris=new Label("            GAMMA DISTRIBUTION    ");
    Label lblalfa=new Label("           α:");
    Label lblbeta=new Label("           β:");
    Label lblx=new Label("           x:");
    Label lblex=new Label("           μ=E(X):");
    Label lblvarx=new Label("         σ2=Var(X):");
    Label lblsdx=new Label("          σ=SD(X):");
    Label lblsecim=new Label("       Secim Yapınız: ");
    TextField txtalfa=new TextField();
    TextField txtbeta=new TextField();
    TextField txtx=new TextField();
    TextField txtex=new TextField();
    TextField txtvarx=new TextField();
    TextField txtsdx=new TextField();
    Button btn1=new Button("P(X>x)");
    Button btn2=new Button("P(X<x)");
    Button btn3=new Button("F(x)");
    NumberAxis xAxis = new NumberAxis(); //area chartin x siniri
    NumberAxis yAxis = new NumberAxis();//area chartin y siniri
    //ac isminde areachart olusturdum.
    final AreaChart<Number,Number> ac = 
            new AreaChart<Number,Number>(xAxis,yAxis);
     
        XYChart.Series series= new XYChart.Series();
    
    public void startLess(TextField txtalfa,TextField txtbeta,TextField txtx){
        double alfax = Double.parseDouble(txtalfa.getText());
        double betax = Double.parseDouble(txtbeta.getText());
        double xx = Double.parseDouble(txtx.getText());
        //0 dan basla ,(alfax/betax)*2 e kadar git ,0.1 artır
        NumberAxis xAxis = new NumberAxis(0,(alfax/betax)*2, 0.1);
        //Gerekli bilesenleri buldum
        findex(alfax,betax);
        findsdx(alfax,betax);
        findvarx(alfax,betax);
        //grafigimizi cizdim
        makeGraphLess(alfax,betax,xx);
        
    }
    public void startMore(TextField txtalfa,TextField txtbeta,TextField txtx){
        double alfax = Double.parseDouble(txtalfa.getText());
        double betax = Double.parseDouble(txtbeta.getText());
        double xx = Double.parseDouble(txtx.getText());
        NumberAxis xAxis = new NumberAxis(0,(alfax/betax)*2, 0.1);
        findex(alfax,betax);
        findsdx(alfax,betax);
        findvarx(alfax,betax);
        makeGraphMore(alfax,betax,xx);
        
    }
    public void startFull(TextField txtalfa,TextField txtbeta,TextField txtx){
        double alfax = Double.parseDouble(txtalfa.getText());
        double betax = Double.parseDouble(txtbeta.getText());
        double xx = Double.parseDouble(txtx.getText());
        NumberAxis xAxis = new NumberAxis(0,(alfax/betax)*2, 0.1);
        findex(alfax,betax);
        findsdx(alfax,betax);
        findvarx(alfax,betax);
        makeGraph(alfax,betax,xx);
        
    }
    public double findex(double alfax, double betax){
        
        double exx=alfax/betax;
        txtex.setText(""+exx);
        return exx;
    }
    public double findsdx(double alfax, double betax){
        
        double sdxx=Math.sqrt(alfax/(betax*betax));
        txtsdx.setText(""+sdxx);
        return sdxx;
    }
    public double findvarx(double alfax, double betax){
        
        double varyansx=alfax/(betax*betax);
        txtvarx.setText(""+varyansx);
        return varyansx;
    }
    public double fxvalue(double xNo,double alfax, double betax){ 
  
    double e=Math.E;
    double fx=(Math.pow(betax, alfax)*Math.pow(xNo, (alfax-1))*Math.pow(e, (-betax*xNo)))/(fak(alfax-1));
      return fx;
    }
    public double fak(double sayi)
  {
      double sonuc=1;
      for(double i=1; i<=sayi; i++)
      {
          sonuc*=i;
      }
      return sonuc;
    
 }
    public void makeGraphMore(double alfax, double betax,double xx ){

    double xAxisLast= (alfax/betax)*2;
   
     for (double i =xx; i <= xAxisLast; i+=0.1) {
        // x ekseninde istenilen noktanın y eksenindeki değerini buldum
        double counter=fxvalue(i,alfax,betax);
        if(counter>alfax/betax){
        counter=0;} //formulasyon kontrol
        //x noktasını ve değeri olan y'i xychartta tanımladim.
        series.getData().add(new XYChart.Data(i,counter));
        }   
     ac.getData().add(series);
    }
    public void makeGraphLess(double alfax, double betax,double xx ){

    double xAxisLast= (alfax/betax)*2;
   
     for (double i =0; i <= xx; i+=0.1) {
        double counter=fxvalue(i,alfax,betax);
     if(counter>alfax/betax){
        counter=0;}
        series.getData().add(new XYChart.Data(i,counter));
        }   
     ac.getData().addAll(series);
    }
    public void makeGraph(double alfax, double betax,double xx ){

    double xAxisLast= (alfax/betax)*2;
   
     for (double i =0; i <= xAxisLast; i+=0.1) {
        double counter=fxvalue(i,alfax,betax);
      if(counter>alfax/betax){
      counter=0;}
        series.getData().add(new XYChart.Data(i,counter));
        }   
     ac.getData().addAll(series);
    }
    @Override
    public void start(Stage stage) {

        //arkaplani ekledim
        GridPane gp = new GridPane();
         txtex.setEditable(false);
         txtsdx.setEditable(false);
         txtvarx.setEditable(false);
        ac.setTitle("Gama Distrubition Graph");
        //grafigin eksenlerine isim verdim
        xAxis.setLabel("x");
        yAxis.setLabel("f(x)");
        //componentleri gridpane e yerleslestirdim.
        gp.addRow(1, lblgiris);
        gp.addRow(2, lblalfa, txtalfa);
        gp.addRow(3, lblbeta, txtbeta);
        gp.addRow(4, lblx, txtx);
        gp.addRow(7, lblex, txtex);
        gp.addRow(8, lblvarx, txtvarx);
        gp.addRow(9, lblsdx, txtsdx);
        gp.addRow(10, lblsecim);
        gp.addColumn(1, btn1, btn2, btn3);
        gp.addColumn(1, ac);

        Scene sc = new Scene(gp, 900, 900);
        //series'i areacharta ekliyor.
        ac.getData().add(series);

        btn1.setOnMouseClicked(a -> {
            series.getData().clear(); //xy graphı sıfırlıyor
            ac.getData().add(series);//areachartı sıfırlıyor
            startMore(txtalfa, txtbeta, txtx);//islemler basliyor
        });
        btn2.setOnMouseClicked(a -> {
            series.getData().clear();
            ac.getData().add(series);
            startLess(txtalfa, txtbeta, txtx);
        });
        btn3.setOnMouseClicked(a -> {
            series.getData().clear();
            ac.getData().add(series);
            startFull(txtalfa, txtbeta, txtx);
        });
        
        stage.setTitle("Gamma Distrubition");
        stage.setScene(sc);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
