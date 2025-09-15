# Logitrack Backend

Backend de simulação e monitoramento de robôs logísticos. Saiu de um CRUD raso para algo que já conta “histórias”: robôs com status, eventos sensoriais e entregas simuladas.

---

## 1. O que Mudou 

Antes: só cadastro de robôs.  
Agora: um pequeno ecossistema com dados coerentes para brincar no front-end, testar filtros, dashboards e evoluir a arquitetura de forma organizada.

Principais ganhos:
- Pacotes reorganizados e namespace novo (`com.ags.logitrack`).
- Banco H2 persistente (dados sobrevivem).
- Seed inteligente gerando cenário “vivo”.
- Camada de serviço (menos bagunça no controller).
- CORS configurado para desenvolvimento com front separado.
- Entidades novas: eventos sensoriais e entregas.

---

## 2. Por que Trocar o Pacote

Trocar `com.robotrack2k` → `com.ags` não foi apenas estética: padroniza o artefato, evita conflito futuro e deu a oportunidade de organizar melhor a estrutura.

---

## 3. pom.xml

Foi explicitado Java 17 (já era assumido). Isso evita alguém compilar sem suporte a features e reclamar depois.  
Também ajustado o `groupId` para acompanhar o pacote.

---

## 4. Banco H2 Persistente

Agora existe `data/logitrackdb.mv.db`.  
Resultado: roda, fecha, abre de novo e os dados continuam. Ótimo para inspeção rápida via `/h2-console`.

---

## 5. Entidades

### RoboLogistico
Representa o “ator principal”. Tem código, modelo, nome, status, bateria e localização.

### EventoSensorial
Pedaços de telemetria: temperatura, proximidade, luz, obstáculos etc. Valores já vêm formatados para exibição fácil.

### EntregaSimulada
Coloca o robô em fluxo logístico: origem, destino, status (PENDENTE, EM_ANDAMENTO, CONCLUÍDA, CANCELADA, ATRASADA), timestamps e (às vezes) distância e observações.

---

## 6. DataInitializer (o Gerador de Cenário)

Quando o banco está vazio:
1. Cria 20 robôs variados (ativos, inativos, manutenção).
2. Gera 3–8 eventos por robô, com datas dos últimos dias.
3. Para robôs ATIVOS, gera 1–5 entregas com combinações realistas (concluída, cancelada, atrasada etc.).

Serve para ter “vida” sem digitar inserts. Útil demais para front e testes de UX.

---

## 7. API de Robôs (atual)

Endpoints principais (base: `/api/robos`):
- GET listar
- GET /{id}
- POST criar
- PUT /{id} atualizar completo
- DELETE /{id}
- PATCH /{id}/status (texto puro)
- PATCH /{id}/bateria (número)

Exemplos:

Criar:
```json
POST /api/robos
{
  "codigo": "RB-021",
  "nome": "Mover Alpha",
  "modelo": "TransportBot 3000",
  "status": "ATIVO",
  "nivelBateria": 76.4,
  "localizacao": "Armazém D - Corredor 1"
}
```

Atualizar status:
```
PATCH /api/robos/5/status
MANUTENCAO
```

Atualizar bateria:
```
PATCH /api/robos/5/bateria
43.8
```

(Controllers para eventos e entregas ainda não expostos.)

---


## 8. Estrutura de Pacotes
A estrutura fina l dos pacotes se encontra assim:
```
com.ags.logitrack
 ├─ config
 │   ├─ CorsConfig
 │   └─ DataInitializer
 ├─ controller
 │   └─ RoboLogisticoController
 ├─ service
 │   └─ RoboLogisticoService
 ├─ model
 │   ├─ RoboLogistico
 │   ├─ EventoSensorial
 │   └─ EntregaSimulada
 └─ repository
     ├─ RoboLogisticoRepository
     ├─ EventoSensorialRepository
     └─ EntregaSimuladaRepository
```

---

## 9. Próximos Passos Naturais

- Validações (bateria 0–100, enums corretos etc.).
- DTOs (desacoplar entidade da borda).
- Filtros e paginação.
- Endpoints para eventos e entregas.
- Swagger/OpenAPI.
- Autenticação.
- Testes (unitário e integração).

---

## 10. Em Uma Frase

De um CRUD estático para uma mini simulação logística pronta para evoluir.

---