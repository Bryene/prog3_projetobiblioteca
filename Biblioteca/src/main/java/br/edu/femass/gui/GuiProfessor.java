package br.edu.femass.gui;

import br.edu.femass.dao.DaoProfessor;
import br.edu.femass.model.Aluno;
import br.edu.femass.model.Professor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GuiProfessor {
    private JFormattedTextField txtNomeProfessor;
    private javax.swing.JPanel JPanel;
    private JFormattedTextField txtEndereco;
    private JFormattedTextField txtTelefone;
    private JFormattedTextField txtDisciplina;
    private JButton btnprofessor;
    private JList lstProfessor;
    private JComboBox<Professor> cboProfessor;

    public JPanel getJPanel() {
        return JPanel;
    }

    public GuiProfessor() {
        updateCombo();
        updateList();
        btnprofessor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Professor professor = new Professor(txtNomeProfessor.getText(), txtEndereco.getText(),
                            txtTelefone.getText(), txtDisciplina.getText());
                    new DaoProfessor().save(professor);
                    professor.atualizarCodigo();
                    updateList();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        lstProfessor.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Professor professor = (Professor) lstProfessor.getSelectedValue();
                if (professor==null) return;
                txtNomeProfessor.setText(professor.getNome());
                txtEndereco.setText(professor.getEndereco());
                txtTelefone.setText(professor.getTelefone());
                txtDisciplina.setText(professor.getDisciplina());
            }
        });
    }

    private void updateList() {
        try {
            List<Professor> professores = new DaoProfessor().getAll();
            lstProfessor.setListData(professores.toArray());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    private void updateCombo() {
        try {
            List<Professor> professores = new DaoProfessor().getAll();
            for (Professor professor : professores) {
                cboProfessor.addItem(professor);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
