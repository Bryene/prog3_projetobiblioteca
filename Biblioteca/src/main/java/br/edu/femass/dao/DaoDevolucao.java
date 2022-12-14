package br.edu.femass.dao;

import br.edu.femass.model.Autor;
import br.edu.femass.model.Emprestimo;
import br.edu.femass.model.Leitor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DaoDevolucao extends Persistencia<Emprestimo> implements Dao<Emprestimo> {

    private final static String NOMEARQUIVO = "devolucoes.json";

    public void save(Emprestimo emprestimo) throws Exception {
        List<Emprestimo> emprestimos = getAll();
        emprestimos.remove((Emprestimo) emprestimos);
        String json = getObjectmapper().writerWithDefaultPrettyPrinter().writeValueAsString(emprestimo);
        FileOutputStream out = new FileOutputStream(NOMEARQUIVO);
        out.write(json.getBytes());
        out.close();
    }

    public List<Emprestimo> getAll() throws Exception {
        try {
            FileInputStream in = new FileInputStream(NOMEARQUIVO);
            String json = new String(in.readAllBytes());
            List<Emprestimo> emprestimos= getObjectmapper().readValue(json, new TypeReference<List<Emprestimo>>(){});
            return emprestimos;
        } catch (FileNotFoundException f) {
            return new ArrayList();
        } catch (Exception e) {
            throw e;
        }
    }
}