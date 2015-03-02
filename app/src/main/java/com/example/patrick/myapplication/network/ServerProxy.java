package com.example.patrick.myapplication.network;

import android.util.Log;

import com.example.patrick.myapplication.bean.NodeBean;
import com.example.patrick.myapplication.bean.Nodes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.Header;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Patrizio Perna on 29/12/14.
 */
public class ServerProxy {

    public static final String DEFAULT_SERVICE_ADDRESS = "https://ninux.nodeshot.org/api/v1";
    private String serviceAddress;

    private String stringTypeJson = "format=json";

    private String limit ="limit=100";



    public ServerProxy() {
        this.serviceAddress = DEFAULT_SERVICE_ADDRESS;
    }


    /**
     * Metodo per ottenere la lista dei nodi dal server corrispondenti  ad una ricerca prestabilita
     * @return ArrayList<NodeBean> array di oggetti di tipo NodeBean
     * @throws IOException
     */
    public ArrayList<NodeBean> getNodesList(String search) throws IOException {

       // eseguo la connessione sicura al server e ottengo la lista nodi in formato json
        String responseString =  connect("/nodes",search);

        // DEBUG
        // Se il server non dovesse essere raggiungibile mi mostra alcuni nodi
        //if (responseString=="")
        //     responseString = nodes_temp;
        //private String nodes_temp= "{\"count\": 2227, \"next\": \"https://ninux.nodeshot.org/api/v1/nodes/?limit=50&page=2&format=json\", \"previous\": null, \"results\": [{\"name\": \"Ateniensi\", \"slug\": \"ateniensi\", \"layer\": \"sicilia\", \"layer_name\": \"Sicilia\", \"user\": \"adriano-mascarella\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [13.266023, 37.490259]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2014-07-26T16:15:31Z\", \"added\": \"2014-07-26T16:07:57Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/ateniensi/\"}, {\"name\": \"Amiatus\", \"slug\": \"amiatus\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"attilio\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [11.633917, 42.900179]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2012-10-03T10:39:42Z\", \"added\": \"2012-10-03T10:38:14Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/amiatus/\"}, {\"name\": \"Ninux Camunia\", \"slug\": \"ninux-camunia\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"fermopermanutenzione\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [10.300165, 45.956511]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2014-12-01T12:39:37Z\", \"added\": \"2014-12-01T10:58:04Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/ninux-camunia/\"}, {\"name\": \"giop\", \"slug\": \"giop\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"giop\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [13.614469, 43.437877]}, \"elev\": null, \"address\": null, \"description\": \"nodo\", \"updated\": \"2013-11-16T17:15:22Z\", \"added\": \"2013-11-16T17:12:05Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/giop/\"}, {\"name\": \"MIDA\", \"slug\": \"mida\", \"layer\": \"pisa\", \"layer_name\": \"Pisa\", \"user\": \"mirko-dalbello\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [10.624509, 43.715007]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2014-06-27T18:39:23Z\", \"added\": \"2014-06-27T18:38:31Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/mida/\"}, {\"name\": \"idealab\", \"slug\": \"idealab\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"s-remo51\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [7.589772, 43.797514]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2014-06-25T09:59:44Z\", \"added\": \"2014-06-25T09:58:03Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/idealab/\"}, {\"name\": \"HitekNET\", \"slug\": \"hiteknet\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"ecolab\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.644963, 41.542819]}, \"elev\": null, \"address\": null, \"description\": \"Hitek\", \"updated\": \"2012-03-12T11:16:15Z\", \"added\": \"2012-03-01T16:21:02Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/hiteknet/\"}, {\"name\": \"Sanbenigno1\", \"slug\": \"sanbenigno1\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"luca\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [7.78015, 45.226856]}, \"elev\": null, \"address\": null, \"description\": \"Router\", \"updated\": \"2014-08-24T22:19:53Z\", \"added\": \"2014-08-24T22:16:26Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/sanbenigno1/\"}, {\"name\": \"rescalda\", \"slug\": \"rescalda\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"luca\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [8.940901, 45.622736]}, \"elev\": 225.0, \"address\": null, \"description\": \"nodo privato rescalda di Luca\", \"updated\": \"2014-12-17T13:56:48Z\", \"added\": \"2014-07-01T14:30:13Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/rescalda/\"}, {\"name\": \"iw4bpe\", \"slug\": \"iw4bpe\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"luca\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [11.04528, 44.26811]}, \"elev\": 732.0, \"address\": null, \"description\": \"Possibile nodo o supernodo\", \"updated\": \"2014-06-28T18:58:16Z\", \"added\": \"2014-06-28T18:51:41Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/iw4bpe/\"}, {\"name\": \"Luca Vets\", \"slug\": \"luca-vets\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"luca\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [11.018096, 42.722016]}, \"elev\": 1.0, \"address\": null, \"description\": \"\", \"updated\": \"2014-06-24T23:00:10Z\", \"added\": \"2014-06-24T22:57:43Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/luca-vets/\"}, {\"name\": \"Albano\", \"slug\": \"albano\", \"layer\": \"calabria\", \"layer_name\": \"Calabria\", \"user\": \"luca\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [16.176939, 39.372526]}, \"elev\": null, \"address\": null, \"description\": \"Anti digital divide\", \"updated\": \"2014-06-02T15:17:59Z\", \"added\": \"2014-06-02T15:17:02Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/albano/\"}, {\"name\": \"Pisa::AmiciDelBuco\", \"slug\": \"pisaamicidelbuco\", \"layer\": \"pisa\", \"layer_name\": \"Pisa\", \"user\": \"luca\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [10.371755, 43.771584]}, \"elev\": 9.0, \"address\": null, \"description\": \"TBD\", \"updated\": \"2014-07-13T22:35:14Z\", \"added\": \"2013-12-22T16:12:31Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/pisaamicidelbuco/\"}, {\"name\": \"ELbuild\", \"slug\": \"elbuild\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"luca\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [10.913207, 43.932089]}, \"elev\": null, \"address\": null, \"description\": \"Ultimo piano di palazzo storico con vista sulla zona sud est della citt\\u00e0.\", \"updated\": \"2013-10-07T22:34:36Z\", \"added\": \"2013-10-07T22:34:08Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/elbuild/\"}, {\"name\": \"Tronix\", \"slug\": \"tronix\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"luca\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [11.092737, 45.974016]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2013-07-31T11:20:24Z\", \"added\": \"2013-07-31T11:15:19Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/tronix/\"}, {\"name\": \"Luca Valmontone\", \"slug\": \"luca-valmontone\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"luca\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.919005, 41.787185]}, \"elev\": 35.0, \"address\": null, \"description\": \"Nodo Chiaccherato\", \"updated\": \"2013-05-05T19:26:56Z\", \"added\": \"2013-05-05T19:26:11Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/luca-valmontone/\"}, {\"name\": \"pisa::iz5ich\", \"slug\": \"pisaiz5ich\", \"layer\": \"pisa\", \"layer_name\": \"Pisa\", \"user\": \"luca\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [10.460775, 43.716174]}, \"elev\": null, \"address\": null, \"description\": \"Luca\", \"updated\": \"2013-05-19T18:11:37Z\", \"added\": \"2013-03-08T10:37:50Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/pisaiz5ich/\"}, {\"name\": \"Larry-01\", \"slug\": \"larry-01\", \"layer\": \"firenze\", \"layer_name\": \"Firenze\", \"user\": \"larry\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [11.327022, 43.961523]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2014-05-13T15:09:26Z\", \"added\": \"2014-05-12T22:45:34Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/larry-01/\"}, {\"name\": \"Alessandro_02\", \"slug\": \"alessandro_02\", \"layer\": \"firenze\", \"layer_name\": \"Firenze\", \"user\": \"alessandro\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [11.319426, 43.586799]}, \"elev\": null, \"address\": null, \"description\": \"Greve in Chianti\", \"updated\": \"2015-01-06T14:49:38Z\", \"added\": \"2015-01-06T14:43:32Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/alessandro_02/\"}, {\"name\": \"Alessandro_01\", \"slug\": \"alessandro_01\", \"layer\": \"firenze\", \"layer_name\": \"Firenze\", \"user\": \"alessandro\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [11.453923, 43.613972]}, \"elev\": null, \"address\": null, \"description\": \"Stecco\", \"updated\": \"2015-01-06T14:45:51Z\", \"added\": \"2014-05-15T14:36:19Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/alessandro_01/\"}, {\"name\": \"Ciamstick\", \"slug\": \"ciamstick\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"alessandro\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [8.925675, 45.606934]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2014-09-05T16:55:09Z\", \"added\": \"2014-09-05T16:54:45Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/ciamstick/\"}, {\"name\": \"AR-FL\", \"slug\": \"ar-fl\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"alessandro\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.488427, 41.792202]}, \"elev\": null, \"address\": null, \"description\": \"via pia nalli\", \"updated\": \"2014-08-14T10:50:51Z\", \"added\": \"2014-08-14T10:50:22Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/ar-fl/\"}, {\"name\": \"JOKER-HACK\", \"slug\": \"joker-hack\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"alessandro\", \"status\": \"active\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.719554, 41.957808]}, \"elev\": null, \"address\": null, \"description\": \"NodoFoglia\", \"updated\": \"2014-07-20T16:11:09Z\", \"added\": \"2014-07-13T12:50:18Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/joker-hack/\"}, {\"name\": \"Pelusky\", \"slug\": \"pelusky\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"alessandro\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.458334, 41.846543]}, \"elev\": null, \"address\": null, \"description\": \"Prvato\", \"updated\": \"2014-07-07T11:36:13Z\", \"added\": \"2014-07-07T11:29:33Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/pelusky/\"}, {\"name\": \"Alex\", \"slug\": \"alex\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"alessandro\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [11.920971, 45.779414]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2014-07-06T17:45:33Z\", \"added\": \"2014-07-05T17:25:50Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/alex/\"}, {\"name\": \"Halnet-Adri\", \"slug\": \"halnet-adri\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"alessandro\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.525235, 41.945785]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2014-05-04T12:02:23Z\", \"added\": \"2014-05-04T12:02:10Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/halnet-adri/\"}, {\"name\": \"Ulisse01\", \"slug\": \"ulisse01\", \"layer\": \"calabria\", \"layer_name\": \"Calabria\", \"user\": \"alessandro\", \"status\": \"active\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [16.23539, 39.304185]}, \"elev\": null, \"address\": null, \"description\": \"Casa di Allessandro Cosenza\", \"updated\": \"2014-08-07T02:34:09Z\", \"added\": \"2014-04-24T20:05:21Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/ulisse01/\"}, {\"name\": \"Ulisse02\", \"slug\": \"ulisse02\", \"layer\": \"calabria\", \"layer_name\": \"Calabria\", \"user\": \"alessandro\", \"status\": \"active\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [16.219087, 39.306928]}, \"elev\": null, \"address\": null, \"description\": \"Casa di Alessandro Castrolibero\", \"updated\": \"2014-12-27T11:29:57Z\", \"added\": \"2014-04-24T20:03:54Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/ulisse02/\"}, {\"name\": \"Halnet-3\", \"slug\": \"halnet-3\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"alessandro\", \"status\": \"active\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.413197, 41.968628]}, \"elev\": null, \"address\": null, \"description\": \"Casa amico halino\", \"updated\": \"2013-10-15T20:54:49Z\", \"added\": \"2013-09-09T12:44:20Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/halnet-3/\"}, {\"name\": \"Halnet-UP\", \"slug\": \"halnet-up\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"alessandro\", \"status\": \"active\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.53387, 41.95476]}, \"elev\": 102.0, \"address\": null, \"description\": \"SuperNode\", \"updated\": \"2014-07-31T21:46:57Z\", \"added\": \"2013-07-24T09:59:13Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/halnet-up/\"}, {\"name\": \"Halnet-2\", \"slug\": \"halnet-2\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"alessandro\", \"status\": \"active\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.412534, 41.968397]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2013-06-24T17:58:36Z\", \"added\": \"2012-09-07T13:26:49Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/halnet-2/\"}, {\"name\": \"nodoromaest\", \"slug\": \"nodoromaest\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"alessandro\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.662774, 41.908103]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2012-06-27T07:54:48Z\", \"added\": \"2012-06-25T14:37:39Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/nodoromaest/\"}, {\"name\": \"Halnet\", \"slug\": \"halnet\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"alessandro\", \"status\": \"active\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.412041, 41.969071]}, \"elev\": null, \"address\": null, \"description\": \"Tetto palazzina a 3 piani con palo di 12 metri.\", \"updated\": \"2014-04-28T10:28:42Z\", \"added\": \"2012-01-02T21:17:56Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/halnet/\"}, {\"name\": \"Ciclamini\", \"slug\": \"ciclamini\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"alessandro\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.56557, 41.893809]}, \"elev\": 0.0, \"address\": null, \"description\": \"Alessandro (LuX friend)\", \"updated\": \"2011-11-14T18:31:58Z\", \"added\": \"2011-10-12T20:14:08Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/ciclamini/\"}, {\"name\": \"Montanara::Cesco\", \"slug\": \"montanaracesco\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"francesco-zanini\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [10.720435, 45.136497]}, \"elev\": 15.0, \"address\": null, \"description\": \"\", \"updated\": \"2012-11-11T15:38:28Z\", \"added\": \"2012-11-05T16:24:18Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/montanaracesco/\"}, {\"name\": \"tiorre\", \"slug\": \"tiorre\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"alsazia-detta-bona\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [10.269814, 44.666638]}, \"elev\": 273.0, \"address\": null, \"description\": \"castello\", \"updated\": \"2014-08-28T01:34:38Z\", \"added\": \"2014-08-28T01:19:28Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/tiorre/\"}, {\"name\": \"Tiziano_Poggio\", \"slug\": \"tiziano_poggio\", \"layer\": \"viterbo\", \"layer_name\": \"Viterbo\", \"user\": \"tiziano-bacocco\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.543554, 42.440021]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2013-11-03T22:39:55Z\", \"added\": \"2013-11-03T22:39:20Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/tiziano_poggio/\"}, {\"name\": \"Tiziano\", \"slug\": \"tiziano\", \"layer\": \"viterbo\", \"layer_name\": \"Viterbo\", \"user\": \"tiziano-bacocco\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.564508, 42.413223]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2013-10-18T06:48:45Z\", \"added\": \"2013-10-18T06:48:29Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/tiziano/\"}, {\"name\": \"massi\", \"slug\": \"massi\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"massi\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.66891, 41.868698]}, \"elev\": null, \"address\": null, \"description\": \"massi\", \"updated\": \"2014-02-03T12:10:12Z\", \"added\": \"2014-02-03T12:08:30Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/massi/\"}, {\"name\": \"federix\", \"slug\": \"federix\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"lucaccioni_federico\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.210799, 43.501998]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2014-07-06T12:17:31Z\", \"added\": \"2014-07-06T12:12:59Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/federix/\"}, {\"name\": \"Pablo\", \"slug\": \"pablo\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"paolo\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.296788, 45.19702]}, \"elev\": 8.0, \"address\": null, \"description\": \"Prova01\", \"updated\": \"2014-09-09T16:07:01Z\", \"added\": \"2014-09-09T16:00:40Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/pablo/\"}, {\"name\": \"sberla2007\", \"slug\": \"sberla2007\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"paolo\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.599827, 42.147138]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2014-08-23T21:26:18Z\", \"added\": \"2014-08-04T00:16:54Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/sberla2007/\"}, {\"name\": \"paolomi\", \"slug\": \"paolomi\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"paolo\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.076877, 45.437994]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2014-10-13T18:41:34Z\", \"added\": \"2014-10-13T18:39:25Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/paolomi/\"}, {\"name\": \"PBM\", \"slug\": \"pbm\", \"layer\": \"default\", \"layer_name\": \"Default\", \"user\": \"paolo\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [8.689553, 45.860212]}, \"elev\": 250.0, \"address\": null, \"description\": \"\", \"updated\": \"2014-06-20T22:02:26Z\", \"added\": \"2014-06-20T22:01:38Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/pbm/\"}, {\"name\": \"ReggioCal::Boschicello\", \"slug\": \"reggiocalboschicello\", \"layer\": \"calabria\", \"layer_name\": \"Calabria\", \"user\": \"paolo\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [15.652327, 38.099837]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2013-09-27T19:41:01Z\", \"added\": \"2013-08-10T14:36:54Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/reggiocalboschicello/\"}, {\"name\": \"CastaniNode\", \"slug\": \"castaninode\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"paolo\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.565936, 41.879107]}, \"elev\": null, \"address\": null, \"description\": \"Nodo via dei castani\", \"updated\": \"2013-03-10T13:16:10Z\", \"added\": \"2013-03-10T11:39:47Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/castaninode/\"}, {\"name\": \"Duur\", \"slug\": \"duur\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"paolo\", \"status\": \"active\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.634553, 41.930379]}, \"elev\": null, \"address\": null, \"description\": \"Nodo foglia\", \"updated\": \"2013-03-15T16:32:57Z\", \"added\": \"2013-02-28T23:15:33Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/duur/\"}, {\"name\": \"bubino\", \"slug\": \"bubino\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"paolo\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.413829, 42.041768]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2012-05-11T06:03:36Z\", \"added\": \"2012-05-11T06:01:19Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/bubino/\"}, {\"name\": \"UccioNet\", \"slug\": \"uccionet\", \"layer\": \"roma\", \"layer_name\": \"Roma\", \"user\": \"paolo\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [12.238422, 42.023318]}, \"elev\": 160.0, \"address\": null, \"description\": \"Connessione da realizzare\", \"updated\": \"2012-01-10T13:31:27Z\", \"added\": \"2012-01-10T13:30:11Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/uccionet/\"}, {\"name\": \"Zabriskie\", \"slug\": \"zabriskie\", \"layer\": \"calabria\", \"layer_name\": \"Calabria\", \"user\": \"nox-perpetua\", \"status\": \"potential\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [16.236999, 39.349001]}, \"elev\": null, \"address\": null, \"description\": \"\", \"updated\": \"2014-06-25T14:44:08Z\", \"added\": \"2014-06-25T14:30:34Z\", \"details\": \"https://ninux.nodeshot.org/api/v1/nodes/zabriskie/\"}]}";


        // eseguo la conversione da json a bean Nodes
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Nodes nodes_list = gson.fromJson(responseString, Nodes.class);

        try{
            return nodes_list.getNodeBean();
        }
        catch(NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Metodo per stabilire una connessione sicura con il server. Viene usata dagli altri metodi per
     * richiedere servizi.
     *
     * @param service E' una stringa che identifica il tipo di servizio di cui ho bisogno
     * @param search E' una stringa che identifica la parola ricercata
     * @return  Restituisce la stringa risultato in formato json
     */
    private String connect(String service,String search){
        try {
            DataLoader dl = new DataLoader();
            HttpResponse response = dl.secureLoadData(DEFAULT_SERVICE_ADDRESS+service+"/?"+"search="+search+"&"+limit+"&"+stringTypeJson);
            Log.i("URL",DEFAULT_SERVICE_ADDRESS+service+"/?"+"search="+search+"&"+limit+"&"+stringTypeJson);

            StringBuilder sb = new StringBuilder();
            sb.append("HEADERS:\n\n");

            Header[] headers = response.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                Header h = headers[i];
                sb.append(h.getName()).append(":\t").append(h.getValue()).append("\n");
            }

            InputStream is = response.getEntity().getContent();
            StringBuilder out = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (String line = br.readLine(); line != null; line = br.readLine())
                out.append(line);
            br.close();

            sb.append("\n\nCONTENT:\n\n").append(out.toString());

            Log.i("response---", out.toString());
            return out.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }




}
