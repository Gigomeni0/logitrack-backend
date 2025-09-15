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
A estrutura final dos pacotes se encontra assim:
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

## 9. Segurança (estado atual e observações)

Estado atual (mínimo para desenvolvimento):
- CORS liberado apenas para http://localhost:8081 (não é wildcard) e com credenciais permitidas.
- H2 Console habilitado em /h2-console.
- Banco H2 file-based com usuário admin e senha simples (ambiente local).
- Endpoints sem autenticação ou autorização.
- PATCH permite alterar status e nível de bateria diretamente sem validação.
- Nenhuma restrição de taxa (rate limiting) ou logging de alteração sensível.

Riscos / pontos de atenção se isso for além de ambiente local:
- H2 Console exposto pode revelar estrutura e dados.
- Credenciais triviais de banco.
- Possibilidade de inserir valores fora de faixa (ex: bateria negativa ou >100).
- CORS com credenciais + ausência de autenticação facilita CSRF/XSS indiretamente em cenários futuros.
- Falta de segregação entre modelos internos e payload externo (sem DTO) abre porta para over-posting.

Decisões implícitas:
- Priorização de velocidade para montar cenário funcional para o front.
- Escopo claramente “dev sandbox” (semente rica + persistência local + ausência de regras rígidas).

Próximos cuidados recomendados:
1. Introduzir autenticação (JWT ou session + Spring Security).
2. Desabilitar H2 Console fora de profile dev.
3. Colocar variáveis sensíveis em .env / profiles.
4. Validar entrada (Bean Validation) e normalizar enums.
5. Adicionar DTOs + mapeamento (ModelMapper ou manual).
6. Definir política de CORS por profile.
7. Auditar mudanças críticas (status, bateria).
8. Preparar migração futura para Postgres ou outro RDBMS.