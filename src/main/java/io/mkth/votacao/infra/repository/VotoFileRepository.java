package io.mkth.votacao.infra.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mkth.votacao.domain.ResultadoVoto;
import io.mkth.votacao.domain.Voto;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VotoFileRepository implements VotoRepositoryInterface {

    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public List<Voto> findAllVotos() {
        return null;
    }

    @Override
    public Voto findVotoById(String id) {
        return null;
    }

    @Override
    public Voto createVoto(Voto voto) {
        FileWriter file;
        try {
            file = createFile("votacao.txt");
            file.write(mapper.writeValueAsString(new ResultadoVoto(voto.getIdUsuario(), voto.getMeuVoto()))+ "\n");
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voto;
    }

    @Override
    public Map<String, Integer> totalVotos() {
        return readFile("votacao.txt");
    }

    private FileWriter createFile(String filename) throws IOException {
        return new FileWriter(filename, true);
    }

    private Map<String, Integer> readFile(String filename) {
        List<String> lines = Collections.emptyList();
        Map<String, Integer> votosDB = new HashMap<>();
        try {
            lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);

            lines.stream()
                    .map(l -> toDTO(l))
                    .forEach(rc -> contabilizandoVotos(rc, votosDB));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return votosDB;
    }

    private ResultadoVoto toDTO(String json) {
        try {
            return mapper.readValue(json, ResultadoVoto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void contabilizandoVotos(ResultadoVoto rc, Map<String, Integer> votosDB) {
        if (votosDB.containsKey(rc.getVoto())) {
            int qnt = votosDB.get(rc.getVoto());
            votosDB.replace(rc.getVoto(), ++qnt);
        } else {
            votosDB.put(rc.getVoto(), 1);
        }
    }

}
