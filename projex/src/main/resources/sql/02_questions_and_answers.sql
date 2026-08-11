INSERT INTO pergunta (id, categoria, enunciado, tipo, pontuacao, ordem, ativo, nivel, quest_id) VALUES
(1, 'FINANCEIRO', 'Qual o principal objetivo do fluxo de caixa?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(2, 'CONTABIL', 'O que caracteriza um ativo no balanço patrimonial?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(3, 'JURIDICO', 'Qual a finalidade de um contrato social?', 'OBJETIVA', 10, 1, TRUE, 'MEDIO', NULL),
(4, 'COMPRAS', 'O que é a cotação de preços no processo de compras?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(5, 'ADMINISTRATIVO', 'Qual das opções descreve a função do organograma?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(6, 'RH', 'Qual a principal função do processo de recrutamento?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(7, 'DEPARTAMENTO_PESSOAL', 'O que representa a folha de pagamento?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(8, 'TREINAMENTO', 'Qual o objetivo do processo de onboarding?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(9, 'HARDWARE', 'O que é a memória RAM de um computador?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(10, 'SOFTWARE', 'O que é um sistema operacional?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(11, 'INFRAESTRUTURA', 'Para que serve um servidor de banco de dados?', 'OBJETIVA', 10, 1, TRUE, 'MEDIO', NULL),
(12, 'REDES', 'Qual a função do protocolo IP em redes de computadores?', 'OBJETIVA', 10, 1, TRUE, 'MEDIO', NULL),
(13, 'SEGURANCA_DA_INFORMACAO', 'O que é a autenticação em dois fatores (2FA)?', 'OBJETIVA', 10, 1, TRUE, 'MEDIO', NULL),
(14, 'SUPORTE_TI', 'O que é um sistema de chamados (SLA/Ticketing)?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(15, 'DESENVOLVIMENTO', 'O que é um controle de versão como o Git?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(16, 'MARKETING', 'O que define o conceito de público-alvo?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(17, 'DESIGN', 'O que é a paleta de cores primária de uma marca?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(18, 'UX_UI', 'Qual é o foco do design de Usabilidade (UX)?', 'OBJETIVA', 10, 1, TRUE, 'MEDIO', NULL),
(19, 'MIDIAS_SOCIAIS', 'O que é o engajamento nas redes sociais?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(20, 'COMERCIAL', 'O que significa o funil de vendas?', 'OBJETIVA', 10, 1, TRUE, 'MEDIO', NULL),
(21, 'VENDAS', 'O que é a abordagem de venda consultiva?', 'OBJETIVA', 10, 1, TRUE, 'MEDIO', NULL),
(22, 'POS_VENDA', 'Qual a importância da pesquisa de satisfação (NPS)?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(23, 'ATENDIMENTO_CLIENTE', 'O que é o tempo médio de atendimento (TMA)?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(24, 'LOGISTICA', 'O que é o controle de estoque FIFO/PEPS?', 'OBJETIVA', 10, 1, TRUE, 'MEDIO', NULL),
(25, 'OPERACIONAL', 'Para que serve o mapeamento de processos?', 'OBJETIVA', 10, 1, TRUE, 'FACIL', NULL),
(26, 'MANUTENCAO', 'O que difere manutenção preventiva de corretiva?', 'OBJETIVA', 10, 1, TRUE, 'MEDIO', NULL);


INSERT INTO resposta (id, pergunta_id, descricao, correta, ordem, ativo) VALUES
-- Pergunta 1
(1, 1, 'Controlar as entradas e saídas de capital financeiro', TRUE, 1, TRUE),
(2, 1, 'Registrar o ponto de funcionários', FALSE, 2, TRUE),
-- Pergunta 2
(3, 2, 'Recursos ou bens controlados que geram benefícios futuros', TRUE, 1, TRUE),
(4, 2, 'Dívidas pendentes com fornecedores', FALSE, 2, TRUE),
-- Pergunta 3
(5, 3, 'Definir as regras de funcionamento e sócios da empresa', TRUE, 1, TRUE),
(6, 3, 'Atestar o recebimento de mercadorias', FALSE, 2, TRUE),
-- Pergunta 4
(7, 4, 'Comparar orçamentos de diferentes fornecedores', TRUE, 1, TRUE),
(8, 4, 'Efetuar o pagamento de salários', FALSE, 2, TRUE),
-- Pergunta 5
(9, 5, 'Representação gráfica da estrutura hierárquica', TRUE, 1, TRUE),
(10, 5, 'Relatório anual de vendas', FALSE, 2, TRUE),
-- Pergunta 6
(11, 6, 'Atrair candidatos qualificados para vagas abertas', TRUE, 1, TRUE),
(12, 6, 'Calcular o imposto de renda retido na fonte', FALSE, 2, TRUE),
-- Pergunta 7
(13, 7, 'Demonstrativo de proventos e descontos dos colaboradores', TRUE, 1, TRUE),
(14, 7, 'Lista de compras de escritório', FALSE, 2, TRUE),
-- Pergunta 8
(15, 8, 'Integrar novos colaboradores à cultura e rotina', TRUE, 1, TRUE),
(16, 8, 'Homologar a demissão de funcionários', FALSE, 2, TRUE),
-- Pergunta 9
(17, 9, 'Memória volátil para armazenamento temporário de dados', TRUE, 1, TRUE),
(18, 9, 'Unidade de armazenamento permanente e offline', FALSE, 2, TRUE),
-- Pergunta 10
(19, 10, 'Software base que gerencia os recursos do hardware', TRUE, 1, TRUE),
(20, 10, 'Uma planilha eletrônica de cálculos', FALSE, 2, TRUE),
-- Pergunta 11
(21, 11, 'Armazenar e gerenciar bases de dados da aplicação', TRUE, 1, TRUE),
(22, 11, 'Imprimir documentos fisicamente', FALSE, 2, TRUE),
-- Pergunta 12
(23, 12, 'Endereçar e rotear pacotes de dados pela rede', TRUE, 1, TRUE),
(24, 12, 'Conectar fisicamente cabos de energia', FALSE, 2, TRUE),
-- Pergunta 13
(25, 13, 'Camada adicional de verificação de identidade', TRUE, 1, TRUE),
(26, 13, 'Alteração diária da senha de rede', FALSE, 2, TRUE),
-- Pergunta 14
(27, 14, 'Ferramenta para registro e acompanhamento de incidentes', TRUE, 1, TRUE),
(28, 14, 'Servidor de email corporativo', FALSE, 2, TRUE),
-- Pergunta 15
(29, 15, 'Sistema de gerenciamento do histórico de código-fonte', TRUE, 1, TRUE),
(30, 15, 'Compilador de arquivos Java', FALSE, 2, TRUE),
-- Pergunta 16
(31, 16, 'Grupo específico de consumidores com perfil ideal', TRUE, 1, TRUE),
(32, 16, 'Todos os concorrentes diretos do mercado', FALSE, 2, TRUE),
-- Pergunta 17
(33, 17, 'Conjunto de cores principais da identidade visual', TRUE, 1, TRUE),
(34, 17, 'Lista de fontes e tipografias utilizadas', FALSE, 2, TRUE),
-- Pergunta 18
(35, 18, 'Garantir uma experiência intuitiva e fluida ao usuário', TRUE, 1, TRUE),
(36, 18, 'Programar o banco de dados da aplicação', FALSE, 2, TRUE),
-- Pergunta 19
(37, 19, 'Interação e envolvimento do público com os conteúdos', TRUE, 1, TRUE),
(38, 19, 'Quantidade de mensagens spam recebidas', FALSE, 2, TRUE),
-- Pergunta 20
(39, 20, 'Mapeamento das etapas da jornada do cliente até a compra', TRUE, 1, TRUE),
(40, 20, 'Lista de produtos fora de estoque', FALSE, 2, TRUE),
-- Pergunta 21
(41, 21, 'Foco em entender a dor do cliente antes de propor a solução', TRUE, 1, TRUE),
(42, 21, 'Forçar a venda através de descontos excessivos', FALSE, 2, TRUE),
-- Pergunta 22
(43, 22, 'Mede o nível de lealdade e satisfação dos clientes', TRUE, 1, TRUE),
(44, 22, 'Calcula a margem de lucro por produto', FALSE, 2, TRUE),
-- Pergunta 23
(45, 23, 'Tempo médio gasto para resolver ou atender um chamado', TRUE, 1, TRUE),
(46, 23, 'Horário de almoço da equipe de suporte', FALSE, 2, TRUE),
-- Pergunta 24
(47, 24, 'O primeiro item que entra no estoque deve ser o primeiro a sair', TRUE, 1, TRUE),
(48, 24, 'O último item fabricado deve ser descartado', FALSE, 2, TRUE),
-- Pergunta 25
(49, 25, 'Visualizar e padronizar o fluxo de trabalho', TRUE, 1, TRUE),
(50, 25, 'Bloquear o acesso da equipe à internet', FALSE, 2, TRUE),
-- Pergunta 26
(51, 26, 'Preventiva evita falhas; corretiva repara falhas ocorridas', TRUE, 1, TRUE),
(52, 26, 'Ambas são idênticas e executadas ao mesmo tempo', FALSE, 2, TRUE);