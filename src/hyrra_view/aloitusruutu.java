package hyrra_view;

import java.util.ArrayList;
import java.util.List;

import hyrra_model.HyrraModel;
import hyrra_model.Pelaaja;
import hyrra_model.PelaajanTiedosto;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class aloitusruutu extends Application {
	
	//Yleiset
	TabPane tabPane;
	ObservableList<Pelaaja> data;
	List<Pelaaja> pelaajat;
	Scene scene;
	HyrraModel hyrrarobo;
	
	//TAB 1
	private Tab tab1;
	private GridPane grid1;
	private ComboBox cboMainPlayer1;
	private ComboBox cboMainPlayer2;
	private Button btnKaynnista;
	private Button btnLukitse;
	private Button btnLopeta;
	private boolean bLukittu = false;
	
	//TAB 2
	private Tab tab2;
	private AnimationTimer timer;
	private ImageView laskuri_1_img = new ImageView();
	private ImageView laskuri_2_img = new ImageView();
	private TextField radiotext1;
	private TextField radiotext2;	
	private TextField txbox3;
	private ToggleGroup group;
	private RadioButton radiobutton1;
	private RadioButton radiobutton2;
	private HBox radiobox1;
	private HBox radiobox2;
	private Button lopetusNappula;
	private Button tallennaNappula;
	
	//TAB 3
	private Tab tab3;
	private GridPane grid3;
	private TableView table;
	
	@Override
	public void start(Stage primaryStage) {
		// Käyttöliittymän rakentaminen
		try {			
			tabPane = new TabPane();
			tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
			
	        BorderPane borderPane = new BorderPane();
	        
	        /*
	        //Tyhjennä pelaajat.dat
			List<Pelaaja> testpelaajat = new ArrayList<Pelaaja>();
			PelaajanTiedosto.kirjoitaTiedosto("pelaajat.dat", testpelaajat);
			*/
	        
	        hyrrarobo = new HyrraModel();
	        
	        //Tab luontikoodit siirretty omiin metodeihinsa
	        createTab1();
	        createTab2();
	        createTab3();
	        
	        //Aseta painikkeiden koodit
	        createButtons();
	        
	        //Aseta view objektien lähtöasetelma (osa painikkeista lukitaan)
	        asetaViewObjektit(1);
	        
	        //Päivitä pelaaja listat
	        updatePelaajatListat();
	        
            //Lisää tabit tabPaneen
            tabPane.getTabs().add(tab1);
            tabPane.getTabs().add(tab2);
            tabPane.getTabs().add(tab3);
	        
	        scene = new Scene(tabPane, 1500, 800);
	        
	        borderPane.prefHeightProperty().bind(scene.heightProperty());
	        borderPane.prefWidthProperty().bind(scene.widthProperty());
	        
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
	};
	
	public void setTabPage(int page) {
		//Ensimmäinen tab on nolla
		tabPane.getSelectionModel().select(page - 1);
	}
	
	public int getMotorSpeed() {
		//Ideana textfield johon voi asettaa nopeuden
		return 50;
	}
	
	public void setTimerKuvat(long time) {
		Image img;
		//long Stringiksi
		String sTime = Long.toString(time);
		if (sTime.length() == 1) {
			//Lisää etunolla jos pituus = 1
			sTime = "0" + sTime; //Esim. 1 -> 01
		}
		
		//Kympit on ensimmäinen kirjain sTime stringissä
		String kympit = String.valueOf(sTime.charAt(0));
		img = new Image(kympit + ".png");
		this.laskuri_1_img.setImage(img);
		
		//Ykköset on toinen kirjain sTime stringissä
		String ykkoset = String.valueOf(sTime.charAt(1));
		img = new Image(ykkoset + ".png");
		this.laskuri_2_img.setImage(img);
	}
	
	private void createTab1() {
		tab1 = new Tab();
		tab1.setText("Päävalikko");
		
		grid1 = new GridPane();
		// etäsyydet
		grid1.setHgap(30); // Horizontal gap
		grid1.setVgap(30); // Vertical gap
		
		//Labelit
		InnerShadow is = new InnerShadow();
		is.setOffsetX(4.0f);
		is.setOffsetY(4.0f);

		Text lblMainPlayer1 = new Text("HYRRÄ 1");		 
		lblMainPlayer1.setEffect(is);
		lblMainPlayer1.setStrokeWidth(3); 
		lblMainPlayer1.setStroke(Color.WHITE); 
		lblMainPlayer1.setFont(Font.font(null, FontWeight.BOLD, 60));
		lblMainPlayer1.setFill(Color.rgb(255, 30, 36));
		
		Text lblMainPlayer2 = new Text("HYRRÄ 2");
		lblMainPlayer2.setEffect(is);
		lblMainPlayer2.setStrokeWidth(3); 
		lblMainPlayer2.setStroke(Color.WHITE); 
		lblMainPlayer2.setFont(Font.font(null, FontWeight.BOLD, 60));
		lblMainPlayer2.setFill(Color.rgb(255, 30, 36));
		
		//Pelaaja 1
		cboMainPlayer1 = new ComboBox();
		cboMainPlayer1.setEditable(true);
		cboMainPlayer1.setStyle("-fx-font-size: 20pt;");

		
		//Pelaaja 2
		cboMainPlayer2 = new ComboBox();
		cboMainPlayer2.setEditable(true);
		cboMainPlayer2.setStyle("-fx-font-size: 20pt;");
		
		//LUKITSE
		btnLukitse = new Button();
		btnLukitse.setText("Lukitse");
    	btnLukitse.setPrefSize(200, 100);
    	btnLukitse.setFont(Font.font("ARIAL", FontWeight.BOLD, 35));
		btnLukitse.setStyle("-fx-background-color: rgb(135,206,235);");
		
		//KÄYNNISTÄ
		btnKaynnista = new Button();
		btnKaynnista.setText("Käynnistä");
    	btnKaynnista.setPrefSize(230, 100);
    	btnKaynnista.setFont(Font.font("ARIAL", FontWeight.BOLD, 35));
    	btnKaynnista.setStyle("-fx-background-color: rgb(135,206,235);");
		
		//LOPETA
		btnLopeta = new Button();
		btnLopeta.setText("Lopeta");
    	btnLopeta.setPrefSize(200, 100);
    	btnLopeta.setFont(Font.font("ARIAL", FontWeight.BOLD, 35));
    	btnLopeta.setStyle("-fx-background-color: rgb(135,206,235);");
		
    	//GRID
		//Sarake 1
		grid1.add(lblMainPlayer1,15,11);
		grid1.add(lblMainPlayer2,16,11);
		//Sarake 2
		grid1.add(cboMainPlayer1,15,12);
		grid1.add(cboMainPlayer2,16,12);
		//Sarake 3
		grid1.add(btnLukitse,15,15);
		grid1.add(btnKaynnista,16,15);
		grid1.add(btnLopeta,17,15);
			
        tab1.setContent(grid1);
        grid1.setStyle("-fx-background-image: url('http://getwallpapers.com/wallpaper/full/8/e/a/1114794-new-beyblade-wallpaper-1920x1200-notebook.jpg')");

	}
	
	private void createTab2() {
		//TAB 2
        tab2 = new Tab();
        tab2.setText("Pelihuone");
        
        GridPane grid = new GridPane();
        grid.setVgap(20);
        grid.setHgap(20);

        txbox3 = new TextField();
        txbox3.setText("0");
        txbox3.setVisible(false);
                    
        //Kuvat ovat hBoxissa
        HBox htimerbox = new HBox();
        htimerbox.getChildren().addAll(laskuri_1_img, laskuri_2_img);
        
        /*
        timer = new AnimationTimer() {
    	    private long timestamp;
    	    private long time = 0;
    	    private long fraction = 0;
    	
    	    @Override
    	    public void start() {
    	        // current time adjusted by remaining time from last run
    	        timestamp = System.currentTimeMillis() - fraction;
    	        super.start();
    	    }
    	
    	    @Override
    	    public void stop() {
    	        super.stop();
    	        // save leftover time not handled with the last update
    	        fraction = System.currentTimeMillis() - timestamp;
    	    }
    	    
    	    @Override
    	    public void handle(long now) {
    	        long newTime = System.currentTimeMillis();
    	        if (timestamp + 1000 <= newTime) {
    	            long deltaT = (newTime - timestamp) / 1000;
    	            time += deltaT;
    	            timestamp += 1000 * deltaT;
    	            txbox3.setText(Long.toString(time));
    	            
    	            //Kutsu
    	            setTimerKuvat(time);
    	        }
    	    }    
    	};
    	*/
    	
    	group = new ToggleGroup();
    	
    	// STOP keskeytys
    	lopetusNappula = new Button("Stop");
    	lopetusNappula.setPrefSize(200, 100);
    	lopetusNappula.setFont(Font.font("ARIAL", FontWeight.BOLD, 35));
    	lopetusNappula.setStyle("-fx-background-color: rgb(135,206,235);");

    	// Tallenna nappula, lähetä eteenpäin tiedot
    	tallennaNappula = new Button("Tallenna");
    	tallennaNappula.setPrefSize(200, 100);
    	tallennaNappula.setFont(Font.font("ARIAL", FontWeight.BOLD, 35));
    	tallennaNappula.setStyle("-fx-background-color: rgb(135,206,235);");
    	
    	//YLEMPI HBOX, RADIOBUTTON1, TEKSTIKENTTÄ 1
    	radiobutton1 = new RadioButton();
    	radiobutton1.setToggleGroup(group);
    	radiobox1 = new HBox();
        
    	radiobox1.setPrefWidth(400);
    	radiobox1.setPadding(new Insets(15, 15, 15, 15));
    	radiobox1.setSpacing(10);
    	
        radiotext1 = new TextField();
        radiotext1.setPrefWidth(300);
    	radiotext1.setPadding(new Insets(15, 15, 15, 15));
    	radiotext1.setStyle("-fx-font-size: 20pt;");
        
    	radiobox1.getChildren().addAll(radiobutton1,radiotext1);
        radiobox1.setStyle("-fx-background-color: rgba(88,88,88,0.6);");
        
    	//ALEMPI HBOX, RADIOBUTTON2, TEKSTIKENTTÄ 2
        InnerShadow is = new InnerShadow();
		is.setOffsetX(4.0f);
		is.setOffsetY(4.0f);
        
		Text voittajalaabeli = new Text("VOITTAJA ON:");
        voittajalaabeli.setEffect(is);
        voittajalaabeli.setStrokeWidth(3); 
        voittajalaabeli.setStroke(Color.WHITE); 
        voittajalaabeli.setFont(Font.font(null, FontWeight.BOLD, 60));
        voittajalaabeli.setFill(Color.rgb(255, 30, 36));
        
        radiobutton2 = new RadioButton();
    	radiobutton2.setToggleGroup(group);
        radiobox2 = new HBox();
        
        radiobox2.setPadding(new Insets(15, 12, 15, 12));
        radiobox2.setSpacing(10);
        radiotext2 = new TextField();
    	radiotext2.setPadding(new Insets(15, 15, 15, 15));
        radiotext2.setPrefWidth(300);
        radiotext2.setStyle("-fx-font-size: 20pt;");
        
        radiobox2.getChildren().addAll(radiobutton2, radiotext2);
        radiobox2.setStyle("-fx-background-color: rgba(88,88,88,0.6);");

    	group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    	    public void changed(ObservableValue<? extends Toggle> ov,
    	        Toggle old_toggle, Toggle new_toggle) {
    	    	if (group.getSelectedToggle().equals(radiobutton1)) {
	                radiobox2.setStyle("-fx-background-color: rgba(88,88,88,0.6);");
		            radiobox1.setStyle("-fx-background-color: rgba(31,250,56,0.6);");
	            } 
	            else if (group.getSelectedToggle().equals(radiobutton2)) {
		            radiobox2.setStyle("-fx-background-color: rgba(31,250,56,0.6);");
	                radiobox1.setStyle("-fx-background-color: rgba(88,88,88,0.6);");
	        	}
    	    }
    	});

    	grid.setStyle("-fx-background-image: url('http://getwallpapers.com/wallpaper/full/8/e/a/1114794-new-beyblade-wallpaper-1920x1200-notebook.jpg')");
        
        /*
        BackgroundImage myBI= new BackgroundImage(new Image("http://getwallpapers.com/wallpaper/full/8/e/a/1114794-new-beyblade-wallpaper-1920x1200-notebook.jpg",1920,1200,false,true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        //then you set to your node
        grid.setBackground(new Background(myBI));
		*/
        	        
        grid.add(htimerbox,24,10);	        
        grid.add(voittajalaabeli, 24, 12);
        grid.add(radiobox1, 24, 13);
        grid.add(radiobox2, 24, 14);
        grid.add(txbox3, 35,1);
        grid.add(lopetusNappula, 28, 10);
        grid.add(tallennaNappula, 28, 12);
        
        tab2.setContent(grid);
	}
	
	private void createTab3() {
		//TAB 3
		tab3 = new Tab();
		tab3.setText("Pistetaulukko");
		
		table = new TableView();
		InnerShadow is = new InnerShadow();
		is.setOffsetX(4.0f);
		is.setOffsetY(4.0f);


		Text otsikko = new Text("HALL OF FAME:");		 
		otsikko.setEffect(is);
		otsikko.setStrokeWidth(3); 
		otsikko.setStroke(Color.WHITE); 
		otsikko.setFont(Font.font(null, FontWeight.BOLD, 80));
		otsikko.setFill(Color.rgb(255, 30, 36));
        
        table.setEditable(true);
        
        TableColumn firstNameCol = new TableColumn("Nimi");
        firstNameCol.setStyle("-fx-font-size: 14pt;");
        firstNameCol.setMinWidth(400);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("nimi"));
        
        TableColumn lastNameCol = new TableColumn("Voitot");
        lastNameCol.setStyle("-fx-font-size: 14pt;");
        lastNameCol.setMinWidth(400);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("voitot"));
        
        TableColumn emailCol = new TableColumn("Aika");
        emailCol.setStyle("-fx-font-size: 14pt;");
        emailCol.setMinWidth(400);
        emailCol.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("aika"));
        
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        table.setPrefHeight(750);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(otsikko, table);
		
        grid3 = new GridPane();
		// etäsyydet
		grid3.setHgap(10); // Horizontal gap
		grid3.setVgap(10); // Vertical gap
		
		// gridin paikka
		grid3.setAlignment(Pos.CENTER);
		grid3.setStyle("-fx-background-image: url('http://getwallpapers.com/wallpaper/full/8/e/a/1114794-new-beyblade-wallpaper-1920x1200-notebook.jpg')");
		grid3.add(vbox, 4, 2);
		
		tab3.setContent(grid3);
	}
	
	public void createButtons() {
		//Telineiden lukitseminen
		btnLukitse.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (bLukittu == true) {
					//Käske robottia lukitsemaan/poistamaan lukitus
					hyrrarobo.LukitseTelineet(false);
					bLukittu = false;
					//Ei lukittu
					btnLukitse.setStyle("-fx-background-color: rgb(135,206,235);");
				} else {
					//Käske robottia lukitsemaan/poistamaan lukitus
					hyrrarobo.LukitseTelineet(true);
					bLukittu = true;
					//Lukittu
					btnLukitse.setStyle("-fx-background-color: rgb(255,204,0);");
				}
				asetaViewObjektit(2);
			}
		});
		//Koodin käynnistäminen
		btnKaynnista.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				radiotext1.setText((String) cboMainPlayer1.getValue());
				radiotext2.setText((String) cboMainPlayer2.getValue());
				//Vaihda sivua tab 2
				tabPane.getSelectionModel().select(tab2);
				//Lähetä robotille komento "BATTLE_START"
				hyrrarobo.KaynnistaOttelu();
				
				//Uusi timer
				timer = new AnimationTimer() {
		    	    private long timestamp;
		    	    private long time = 0;
		    	    private long fraction = 0;
		    	
		    	    @Override
		    	    public void start() {
		    	        // current time adjusted by remaining time from last run
		    	        timestamp = System.currentTimeMillis() - fraction;
		    	        super.start();
		    	    }
		    	
		    	    @Override
		    	    public void stop() {
		    	        super.stop();
		    	        // save leftover time not handled with the last update
		    	        fraction = System.currentTimeMillis() - timestamp;
		    	    }
		    	
		    	    @Override
		    	    public void handle(long now) {
		    	        long newTime = System.currentTimeMillis();
		    	        if (timestamp + 1000 <= newTime) {
		    	            long deltaT = (newTime - timestamp) / 1000;
		    	            time += deltaT;
		    	            timestamp += 1000 * deltaT;
		    	            txbox3.setText(Long.toString(time));
		    	            
		    	            //Kutsu
		    	            setTimerKuvat(time);
		    	        }
		    	    }    
		    	};
				timer.start();
				asetaViewObjektit(3);
			}
		});
		//Sekuntikellon pysäyttäminen
		lopetusNappula.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Lähetä robotille komento "STOP"
				hyrrarobo.LopetaOttelu();
				//Pysäytä sekuntikello
				timer.stop();
				//Jatkotoimenpiteet
				//Ei lukittu
				btnLukitse.setStyle("-fx-background-color: rgb(135,206,235);");
				//tallenna painike disabled = false;
				asetaViewObjektit(4);
			}
		});
		//Tallenna pelaaja tiedostoon
		tallennaNappula.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				tallennaVoittajaPelaaja();
				asetaViewObjektit(5);
			}
		});
		//Ohjelman sulkeminen
		btnLopeta.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Lähetä robotille komento "STOP"
				hyrrarobo.SuljeRobotti();
				//Sulje ohjelma
				System.exit(0);
			}
		});
	}
	
	//Tähän kaikki painikkeiden ja tekstikenttien disablointi ja muu ulkoasun muuttaminen
	private void asetaViewObjektit(int vaihe) {
		//Alkutilanne
		if (vaihe == 1) {
			//Hyrrä 1 ja 2 tyhjät
			//Tab 1
			cboMainPlayer1.setValue("");
			cboMainPlayer2.setValue("");
			//Tab 2
			radiotext1.setText("");
			radiotext2.setText("");
			//Timer nolla
			txbox3.setText("0");
			setTimerKuvat(0);
			//Käynnistä lukittu
			btnKaynnista.setDisable(true);
			//Stop timer lukittu
			lopetusNappula.setDisable(true);
			//Tallenna pelaaja lukittu
			tallennaNappula.setDisable(true);
			//Lukittu väri default harmaa
			
		//Lukitse painiketta painetaan
		} else if (vaihe == 2) {
			if (bLukittu == true) {
				//Hyrräteline lukittu
				btnKaynnista.setDisable(false);
				//Hyrrä 1 ja Hyrrä 2 ei voi enään muuttaa
				cboMainPlayer1.setDisable(true);
				cboMainPlayer2.setDisable(true);
				//Lukitse väri keltainen
				//btnLukitse
				
			} else {
				//Hyrrätelineen lukitus poistettu
				btnKaynnista.setDisable(true);
				//Hyrrä 1 ja Hyrrä 2 voi muuttaa
				cboMainPlayer1.setDisable(false);
				cboMainPlayer2.setDisable(false);
				//Lukittu väri default harmaa
				//btnLukitse
			}
		//Käynnistä painiketta painetaan
		} else if (vaihe == 3) {
			setTabPage(2);
			//Tyhjennä timer
			txbox3.setText("0");
			setTimerKuvat(0);
			//Poista stop painike lukitus
			lopetusNappula.setDisable(false);
			//Käynnistä disabled
			btnKaynnista.setDisable(true);
			btnLukitse.setDisable(true);
			radiobox1.setStyle("-fx-background-color: rgba(88,88,88,0.6);");
			radiobox2.setStyle("-fx-background-color: rgba(88,88,88,0.6);");
			//Focus stop painikkeeseen
			lopetusNappula.requestFocus();
		//Lopeta painiketta painetaan
		} else if (vaihe == 4) {
			//Hyrrä 1 ja 2 tyhjät
			cboMainPlayer1.setValue("");
			cboMainPlayer2.setValue("");
			cboMainPlayer1.setDisable(false);
			cboMainPlayer2.setDisable(false);
			//Lukitse painike disable pois
			btnLukitse.setDisable(false);
			//Lukitse lopetus painike
			lopetusNappula.setDisable(true);
			//Tallenna lukitus poistettu
			tallennaNappula.setDisable(false);
			bLukittu = false;
		//Tallenna pelaaja painike
		} else if (vaihe == 5) {
			//Avaa tab 3
			setTabPage(3);
			//Tab 2
			radiotext1.setText("");
			radiotext2.setText("");
			radiobox1.setStyle("-fx-background-color: rgba(88,88,88,0.6);");
			radiobox2.setStyle("-fx-background-color: rgba(88,88,88,0.6);");
			//Käynnistä lukittu
			btnKaynnista.setDisable(true);
			//Stop timer lukittu
			lopetusNappula.setDisable(true);
			//Tallenna pelaaja lukittu
			tallennaNappula.setDisable(true);
		}
	}
	
	public void tallennaVoittajaPelaaja() {
		//Tarkista valittu voittaja
		String voittajanimi;
		int aika;
		
		//Voittajan nimi
		if (group.getSelectedToggle().equals(radiobutton1)) {
			voittajanimi = radiotext1.getText();
		} else if (group.getSelectedToggle().equals(radiobutton2)) {
			voittajanimi = radiotext2.getText();
		} else {
			//Kumpaakaan ei ole valittu
			return;
		}
		
		//Ottelun aika
		aika = Integer.parseInt(txbox3.getText());
		
		//Hae pelaajat tiedostosta
		pelaajat = PelaajanTiedosto.lueTiedosto("pelaajat.dat");
		boolean löytyi = false;
		int voitot;
		//looppaa pelaajat for loopilla ja tarkista löytyykö voittajanimi jo tiedostosta
		
		for (Pelaaja pelaaja : pelaajat) {
			System.out.println(pelaaja.getNimi() );
			System.out.println(voittajanimi);
			if (pelaaja.getNimi().equals(voittajanimi)) {
				löytyi = true;
				System.out.println(" Nyt se on sisällä");
				//Kasvata pelaajan voittoja yhdellä
				voitot = pelaaja.getVoitot();
				voitot++;
				//setVoitto
				pelaaja.setVoitot(voitot);
				//Vertaa pelaajan parasta aikaa ja edellistä aikaa
				if (aika > pelaaja.getAika()) { 
				//Tallenna parempi aika
					pelaaja.setAika(aika);
				}
				break;
			}		
		}
		
		//jos loop päättyy ja pelaajaa ei ole löytynyt niin lisää pelaaja listaan
			//pelaajat.add(new Pelaaja
		if (löytyi == false) {
			voitot = 1;
			pelaajat.add(new Pelaaja(voittajanimi, voitot, aika));
		} else if (löytyi == true) {
			//Pelaajaa ei tarvitse lisätä
		}
		
		//Tallenna uusi pelaaja lista tiedostoon
		//kirjoitaTiedosto("pelaajat.dat", pelaajat
		PelaajanTiedosto.kirjoitaTiedosto("pelaajat.dat", pelaajat );
		//Kutsu listojenpäivitys metodipa
		
		updatePelaajatListat();
	}
	
	//Tätä kutsutaan kun ohjelma aukeaa ja kun voittaja tallennetaan
	public void updatePelaajatListat() {
		
		pelaajat = new ArrayList<Pelaaja>();
		
		//Lue pelaajat tiedostosta
		pelaajat = PelaajanTiedosto.lueTiedosto("pelaajat.dat");
		
		//Aseta pelaajat lista tab 1 comboboxeihin
		for (Pelaaja pelaaja : pelaajat) {
			//Aseta pelaaja comboboxien sisältö
			cboMainPlayer1.getItems().add(pelaaja.getNimi());
			cboMainPlayer2.getItems().add(pelaaja.getNimi());
		}
		
		//Aseta pelaajat tab 3 listaan
		data = FXCollections.observableArrayList(pelaajat);
		
		//Aseta Hall of Fame listan sisältö
		table.setItems(data);
	}
	
	// JavaFX-sovelluksen (käyttöliittymän) käynnistäminen
	public static void main(String[] args) {
		launch(args);
	}
}