# Mudanças Marcantes da Atualização

## Mudança de Identidade / Namespace

- Pacote raiz renomeado de `com.robotrack2k.logitrack` para `com.ags.logitrack`.
- `groupId` no `pom.xml` alterado para `com.ags`.
- Arquivo `LogitrackApplication.java` movido para novo pacote.

## Ampliação do Domínio de Dados

- Antes: foco em CRUD simples (robôs).
- Agora: adicionadas entidades `RoboLogistico`, `EventoSensorial`, `EntregaSimulada`.

## Repositórios JPA Correspondentes

- Inclusão de `EventoSensorialRepository` e `EntregaSimuladaRepository`, além de `RoboLogisticoRepository`.

## Seed Automático de Dados

- Classe `DataInitializer` cria cenário inicial: 20 robôs, eventos sensoriais e entregas.
- Executado somente se `roboLogisticoRepository.count() == 0`.

## Persistência Local File-Based

- H2 configurado em modo file (`jdbc:h2:file:./data/logitrackdb`).
- Diretório `data/` com arquivo físico `logitrackdb.mv.db`.

## Novos Endpoints REST

- Controller `RoboLogisticoController` com CRUD completo e `PATCH` específicos:
  - `PATCH /api/robos/{id}/status`
  - `PATCH /api/robos/{id}/bateria`
- Uso de `ResponseEntity` em todas as respostas.

## Camada de Serviço

- `RoboLogisticoService` centraliza operações e atualizações parciais.

## Configuração de CORS

- Classe `CorsConfig` permitindo origem `http://localhost:8081`.

## Ajustes de Build / Plataforma

- `maven.compiler.source` e `maven.compiler.target` definidos para Java 17.
- Dependências principais consolidadas (Web, JPA, H2, Lombok, Test).

## Atualização do README

- README antigo removido; novo README detalhado (substituído agora por esta versão abreviada de mudanças).

## Console H2 Ativado

- `spring.h2.console.enabled=true` em `/h2-console`.
- Credenciais simples (`admin` / `pass`) para desenvolvimento.

## Exposição Direta das Entidades

- Sem DTOs ou validação (exposição direta das entidades JPA nas respostas HTTP).

## Estrutura de Pacotes Expandida

```text
config/ (CorsConfig, DataInitializer)
controller/ (RoboLogisticoController)
service/ (RoboLogisticoService)
model/ (RoboLogistico, EventoSensorial, EntregaSimulada)
repository/ (...)
```

## Dados Operacionais Mais Ricos

- Eventos sensoriais simulam cenários reais (proximidade, temperatura, luminosidade, etc.).
- Entregas possuem status, timestamps, distância (quando concluídas) e observações condicionais.

## Base Preparada para Evolução

- Facilita futura inclusão de filtros, paginação, segurança, métricas e validações de domínio.

