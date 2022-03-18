package io.mkth.votacao.infra.repository;

import io.mkth.votacao.domain.Voto;

import java.util.List;
import java.util.Map;

public interface VotoRepositoryInterface {
    List<Voto> findAllVotos();
    Voto findVotoById(String id);
    Voto createVoto(Voto voto);
    Map<String, Integer> totalVotos();
}
