/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

/**
 *
 * @author brahim
 */
public class NewJFrame extends javax.swing.JFrame {

    WriterGA logWriterGa;
    GeneticAlgorithm ga;
    int MAX_RUN_GA;
    int MAX_LENGTH_GA;
    long[] runtimesGA;

    Writer logWriter;
    ArtificialBeeColony abc;
    int MAX_RUN;
    int MAX_LENGTH;
    long[] runtimes;

    public void TesterABC() {
        logWriter = new Writer();
        MAX_RUN = 50;
        runtimes = new long[MAX_RUN];
    }

    public void TesterGA() {
        logWriterGa = new WriterGA();
        MAX_RUN_GA = 50;
        runtimesGA = new long[MAX_RUN_GA];
    }

    public void testABC(int maxLength, int trialLimit, int maxEpoch) {
        MAX_LENGTH = maxLength;
        abc = new ArtificialBeeColony(MAX_LENGTH); // instantiate and define abc here
        abc.setLimit(trialLimit);
        abc.setMaxEpoch(maxEpoch);
        long testStart = System.nanoTime();
        String filepath = "ABC-N" + MAX_LENGTH + "-" + trialLimit + "-" + maxEpoch + ".txt";
        long startTime = 0;
        long endTime = 0;
        long totalTime = 0;
        int fail = 0;
        int success = 0;

        logParametersABC();

        for (int i = 0; i < MAX_RUN;) { // run 50 sucess to pass passing criteria
            startTime = System.nanoTime();
            if (abc.algorithm()) {
                endTime = System.nanoTime();
                totalTime = endTime - startTime;

                System.out.println("Done");
                System.out.println("run " + (i + 1));
                System.out.println("time in nanoseconds: " + totalTime);
                System.out.println("Success!");

                runtimes[i] = totalTime;
                i++;
                success++;

                // write to log
                logWriter.add((String) ("Run: " + i));
                logWriter.add((String) ("Runtime in nanoseconds: " + totalTime));
                logWriter.add((String) ("Found at epoch: " + abc.getEpoch()));
                logWriter.add((String) ("Population size: " + abc.getPopSize()));
                logWriter.add("");

                for (Honey h : abc.getSolutions()) { // write solutions to log file
                    logWriter.add(h);
                    logWriter.add("");
                }
            } else { // count failures for failing criteria
                fail++;
                System.out.println("Fail!");
            }

            if (fail >= 100) {
                System.out.println("Cannot find solution with these params");
                break;
            }
            startTime = 0; // reset time
            endTime = 0;
            totalTime = 0;
        }

        System.out.println("Number of Success: " + success);
        System.out.println("Number of failures: " + fail);
        logWriter.add("Runtime summary");
        logWriter.add("");

        for (int x = 0; x < runtimes.length; x++) { // print runtime summary
            logWriter.add(Long.toString(runtimes[x]));
        }

        long testEnd = System.nanoTime();
        logWriter.add(Long.toString(testStart));
        logWriter.add(Long.toString(testEnd));
        logWriter.add(Long.toString(testEnd - testStart));

        logWriter.writeFile(filepath);
        printRuntimesABC();
    }

    public void testGA(int maxLength, double mutationRate, int maxEpoch) {
        MAX_LENGTH_GA = maxLength;
        ga = new GeneticAlgorithm(MAX_LENGTH_GA); // define ga here
        ga.setMutation(mutationRate);
        ga.setEpoch(maxEpoch);
        long testStart = System.nanoTime();
        String filepath = "GA-N" + MAX_LENGTH_GA + "-" + mutationRate + "-" + maxEpoch + ".txt";
        long startTime = 0;
        long endTime = 0;
        long totalTime = 0;
        int fail = 0;
        int success = 0;

        logParametersGA();

        for (int i = 0; i < MAX_RUN_GA;) { // run 50 sucess to pass passing criteria
            startTime = System.nanoTime();
            if (ga.algorithm()) {
                endTime = System.nanoTime();
                totalTime = endTime - startTime;

                System.out.println("Done");
                System.out.println("run " + (i + 1));
                System.out.println("time in nanoseconds: " + totalTime);
                System.out.println("Success!");

                runtimesGA[i] = totalTime;
                i++;
                success++;

                // write to log
                logWriterGa.add((String) ("Run: " + i));
                logWriterGa.add((String) ("Runtime in nanoseconds: " + totalTime));
                logWriterGa.add((String) ("Found at epoch: " + ga.getEpoch()));
                logWriterGa.add((String) ("Population size: " + ga.getPopSize()));
                logWriterGa.add("");

                for (Chromosome c : ga.getSolutions()) { // write solutions to log file
                    logWriterGa.add(c);
                    logWriterGa.add("");
                }
            } else { // count failures for failing criteria
                fail++;
                System.out.println("Fail!");
            }

            if (fail >= 100) {
                System.out.println("Cannot find solution with these params");
                break;
            }
            startTime = 0; // reset time
            endTime = 0;
            totalTime = 0;
        }

        System.out.println("Number of Success: " + success);
        System.out.println("Number of failures: " + fail);
        logWriterGa.add("Runtime summary");
        logWriterGa.add("");

        for (int x = 0; x < runtimesGA.length; x++) { // print runtime summary
            logWriterGa.add(Long.toString(runtimesGA[x]));
        }

        long testEnd = System.nanoTime();
        logWriterGa.add(Long.toString(testStart));
        logWriterGa.add(Long.toString(testEnd));
        logWriterGa.add(Long.toString(testEnd - testStart));

        logWriterGa.writeFile(filepath);
        printRuntimesGA();
    }

    public void logParametersABC() {
        logWriter.add("Artificial Bee Colony Algorithm");
        logWriter.add("Parameters");
        logWriter.add((String) ("MAX_LENGTH/N: " + MAX_LENGTH));
        logWriter.add((String) ("STARTING_POPULATION: " + abc.getStartSize()));
        logWriter.add((String) ("MAX_EPOCHS: " + abc.getMaxEpoch()));
        logWriter.add((String) ("FOOD_NUMBER: " + abc.getFoodNum()));
        logWriter.add((String) ("TRIAL_LIMIT: " + abc.getLimit()));
        logWriter.add((String) ("MINIMUM_SHUFFLES: " + abc.getShuffleMin()));
        logWriter.add((String) ("MAXIMUM_SHUFFLES: " + abc.getShuffleMax()));
        logWriter.add("");
    }

    public void printRuntimesABC() {
        for (long x : runtimes) {
            System.out.println("run with time " + x + " nanoseconds");
        }
    }

    public void logParametersGA() {
        logWriterGa.add("Genetic Algorithm");
        logWriterGa.add("Parameters");
        logWriterGa.add((String) ("MAX_LENGTH/N: " + MAX_LENGTH_GA));
        logWriterGa.add((String) ("STARTING_POPULATION: " + ga.getStartSize()));
        logWriterGa.add((String) ("MAX_EPOCHS: " + ga.getMaxEpoch()));
        logWriterGa.add((String) ("MATING_PROBABILITY: " + ga.getMatingProb()));
        logWriterGa.add((String) ("MUTATION_RATE: " + ga.getMutationRate()));
        logWriterGa.add((String) ("MIN_SELECTED_PARENTS: " + ga.getMinSelect()));
        logWriterGa.add((String) ("MAX_SELECTED_PARENTS: " + ga.getMaxSelect()));
        logWriterGa.add((String) ("OFFSPRING_PER_GENERATION: " + ga.getOffspring()));
        logWriterGa.add((String) ("MINIMUM_SHUFFLES: " + ga.getShuffleMin()));
        logWriterGa.add((String) ("MAXIMUM_SHUFFLES: " + ga.getShuffleMax()));
        logWriterGa.add("");
    }

    public void printRuntimesGA() {
        for (long x : runtimesGA) {
            System.out.println("run with time " + x + " nanoseconds");
        }
    }

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImages(null);
        getContentPane().setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("N Queen Problem solving");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(160, 30, 490, 80);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("Genetic Algorithm GA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(160, 160, 250, 160);

        jLabel3.setFont(new java.awt.Font("Pristina", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("check the console to see the resulte");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(260, 370, 240, 60);

        jLabel5.setBackground(new java.awt.Color(102, 102, 255));
        jLabel5.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\brahim\\OneDrive\\Documents\\NetBeansProjects\\JavaApplication3\\png-clipart-galaxy-macau-chromosome-disease-genetics-medicine-others-miscellaneous-biology_1-removebg-preview.png")); // NOI18N
        jLabel5.setText("Genetic Algorithm GA");
        jLabel5.setAlignmentX(2.0F);
        getContentPane().add(jLabel5);
        jLabel5.setBounds(0, 170, 410, 140);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 396, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 160, 400, 160);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)));
        jPanel2.setLayout(null);

        jButton2.setText("jButton2");
        jPanel2.add(jButton2);
        jButton2.setBounds(969, 124, 73, 23);

        jButton3.setText("jButton3");
        jPanel2.add(jButton3);
        jButton3.setBounds(760, 89, 73, 23);

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setText("Artificial Bee Colony ABC");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4);
        jButton4.setBounds(100, 0, 270, 160);

        jLabel4.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/download-removebg-preview 1.png"))); // NOI18N
        jLabel4.setText("Artificial Bee Colony ABC");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(10, 50, 258, 70);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(420, 160, 370, 160);

        jLabel6.setFont(new java.awt.Font("Pristina", 0, 40)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Select an algorithm ");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(270, 100, 260, 60);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/285572340_827532434874760_584895845672867491_n 1.png"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 830, 440);

        jLabel7.setText("jLabel7");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(330, 380, 34, 14);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
        TesterABC tester = new TesterABC();
        tester.test(4, 50, 1000);
    }// GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        TesterGA tester = new TesterGA();
        tester.test(4, 0.001, 1000);
    }// GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new NewJFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

}
