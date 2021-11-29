
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class MyWorker extends SwingWorker<Void, Integer>{

    List<String> files;
    String origen;
    String destination;
    JProgressBar folderBar;
    JProgressBar fileBar;
    JButton cancelButton;
    JLabel folderLabel;
    JLabel fileLabel;
    MainFrame mainFrame;
    String nameFolder; 
    
    public MyWorker(MainFrame mainFrame) {
        this.files = mainFrame.getFiles();
        this.folderBar = mainFrame.getFolderProgressBar();
        this.destination = mainFrame.getDestination();
        this.origen = mainFrame.getOrigen();
        this.fileBar = mainFrame.getFileProgressBar();
        this.cancelButton= mainFrame.getCancelButton();
        this.folderLabel = mainFrame.getFolderLabel();
        this.fileLabel= mainFrame.getFileLabel();
        folderBar.setEnabled(true);
        fileBar.setEnabled(true);
        this.mainFrame = mainFrame;
        this.nameFolder = mainFrame.getNameFolder();
    }

    
    @Override
    protected Void doInBackground() throws Exception {
        folderLabel.setText("You're compriming '"+nameFolder+"'.");
        doZip();
        return null;
    }
    
    public void doZip(){
        int BUFFER_SIZE = 1024;
        double cont2 = 0.0;
        double dif2 = 0.0;
        int cont=0;
        int dif = 100/files.size();
        try{ 
             
            BufferedInputStream origin = null; 
            FileOutputStream dest = new FileOutputStream(destination+"\\folder.zip"); 
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest)); 
            
            byte[] data = new byte[BUFFER_SIZE]; 
            Iterator i = files.iterator();
            while(i.hasNext()){ 
                
                folderBar.setValue(cont+=dif);
                
                
                String filename = (String)i.next(); 
                fileLabel.setText("You're compriming the folder '"+filename+"'.");
                FileInputStream fi = new FileInputStream(origen+"\\"+filename);
                origin = new BufferedInputStream(fi, BUFFER_SIZE); 
                ZipEntry entry = new ZipEntry( filename ); 
                out.putNextEntry( entry ); 
                
                int count; 
                cont2 = 0.; 
                dif2 = 102400/(double)fi.getChannel().size();
                while((count = origin.read(data, 0, BUFFER_SIZE)) != -1){
                    out.write(data, 0, count); 
                    fileBar.setValue((int)cont2);
                    cont2+=dif2;
                } 
                    fileBar.setValue(100);
                 
                origin.close(); 
            } 
           
            out.close(); 
            folderBar.setValue(100);
        }catch( Exception e ){ 
          //e.printStackTrace(); 
        } 

    }
    
    
    @Override
    protected void done() {
        folderLabel.setText("");
        fileLabel.setText("");
        fileBar.setValue(0);
        cancelButton.setEnabled(false);
        JOptionPane.showMessageDialog(mainFrame, "The compression of '"+nameFolder+"' has been finished.", "Compression Information.", JOptionPane.INFORMATION_MESSAGE);
        mainFrame.resetAll();
    }
}
