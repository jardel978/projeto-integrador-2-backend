DMH - O Seu Banco Digital ![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.001.jpeg)![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.002.png)

Olá, seja bem-vindo ao app DMH - O Seu Banco Digital. Te convidamos a conhecer um pouco mais sobre o nosso projeto. 

Lidar com dinheiro nem sempre foi fácil e a pouco tempo atrás, antes da revolução  tecnológica  a  qual  presenciamos  corriqueiramente,  além  de difícil era burocrático. Se você já perdeu horas do seu precioso tempo em filas de bancos para realizar uma simples transação de depósito sabe do que estamos falando. ![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.003.png)

E pensando nisso, compartilhando também da sua dor: nasceu a Digital Money  House,  uma  solução  que  busca  democratizar  e  descomplicar  a gestão e a movimentação de recursos financeiros de seus usuários. 

O projeto em questão foi proposto como Projeto Integrador de Conclusão do segundo ano do curso Certified Tech Developer, oferecido pela Digital House em parceria com o Mercado Livre e a Globant, estruturado como especialização na stack back-end. 

Funcionalidades ![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.004.png)

1. Realizar o registro na Digital Money House; 
1. Validar e-mail ao se cadastrar; 
1. Fazer login ;
1. Acessar e usar os serviços oferecidos para sua conta digital: 
1. Fazer transferências de fundos; 
1. Consultar  seus  dados  pessoais  carregados  no  momento do registro; 
1. Consultar os dados da sua conta virtual ; 
1. Adicionar cartões de débito e/ou crédito; 
1. Carregar saldo em sua carteira; 
1. Consultar  os cartões de débito e/ou crédito associados a sua conta; 
1. Remover um cartão de débito e/ou crédito quando não quiser mais usá-lo na carteira; 
1. Depositar  dinheiro  de  seu  cartão  de  débito  e/ou  crédito  na carteira Digital Money House; 
1. Enviar/transferir  dinheiro  para  uma outra conta do seu saldo disponível na carteira Digital Money House; 
1. Consultar as últimas 10 transferências; 
1. Recuperar sua senha via e-mail; 
12. Baixar um arquivo como prova da transferência; 
12. Contas para as quais transferiu dinheiro; 
12. Todas as atividades realizadas (entrada e saída de dinheiro); 
12. Detalhes de uma atividade específica; 
12. Filtrar suas atividades por um valor aproximado; 
5. Encerrar a sessão na carteira Digital Money House; 

Tecnologias e conceitos empregados ![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.003.png)![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.005.png)

1. **Cloud Computing:** 

É uma tecnologia que usa a conectividade e a grande escala da Internet  para  hospedar  os  mais  variados  recursos,  programas  e informações de forma remota. 

1. Plataforma  provedora  de  computação  em  nuvem:  AWS (Amazon Web Service) 
1. Recursos utilizados: 
   1. Instância EC2; 
1. Diagrama  de  Arquitetura  de  Cloud: 

![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.006.jpeg)

2. **Git:** 

É  um  sistema  de  controle  de  versões  distribuído,  usado principalmente no desenvolvimento de software; 

3. **Oauth2:** 

É  um  protocolo  de  autorização  que  permite  que  aplicativos obtenham acesso limitado a contas de usuários em um serviço HTTP sem  a  necessidade  de enviar seu usuário e senha. Basicamente, o ![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.003.png)usuário delega, a um determinado aplicativo, acesso aos seus dados em um determinado serviço ou API; 

4. **Postman:** 

É  uma  plataforma  de  API  para  desenvolvedores  projetarem, construírem, testarem e iterarem suas APIs; 

5. **MySQL:** 

Um  sistema  open-source  de  gerenciamento  de base de dados relacional; 

6. **Docker:** 

O Docker é uma plataforma open source que facilita a criação e administração  de  ambientes  isolados.  Ele  possibilita  o empacotamento  de  uma  aplicação  ou  ambiente  dentro  de  um container,  se  tornando  portátil  para  qualquer  outro  host  que  o contenha instalado. 

Ele proporciona criar, implantar, copiar e migrar de um ambiente para outro com maior flexibilidade e praticidade. 

1. Containers: ![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.007.jpeg)
2. Arquivo Docker Compose: 

![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.008.jpeg) ![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.009.jpeg)

7. **Linguagens: ![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.003.png)**

Java em sua versão 17; 

**VIII.  Microsserviços:** 

Microsserviços são uma abordagem de arquitetura para a criação de aplicações que são distribuídas e levemente acopladas para que as  mudanças  feitas  por  uma  equipe  não  corrompam  toda  a aplicação, com isso as equipes de desenvolvimento conseguem criar rapidamente novos componentes de app para atender às dinâmicas necessidades da empresa; 

Para esse projeto temos os seguintes microsserviços: 

1. Eureka: Monitoramento dos serviços disponíveis; 
1. Config Server: Provedor de arquivos de configuração; 
1. Gateway: Controle de fluxo de acesso ao ecossistema; 
1. Keycloak: Controle de acesso e gerenciamento de usuários; 
1. Users: Serviço de usuários; 
1. Accounts: Serviços de Contas, Cartões e Transações ; 
9. **Keycloak:** 

É  uma  ferramenta  criada  pela  empresa  Red  Hat  e  que  faz  o gerenciamento de credenciais de usuários e de suas permissões; 

10. **API RESTful:** 

API RESTful é aquela que está em conformidade com os critérios estabelecidos  pela  Transferência  de  Estado  Representacional (REST); ![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.003.png)

O estilo de arquitetura REST representa um conjunto específico de  princípios  que,  se  aplicados  no  desenvolvimento  de  uma aplicação  web,  caracterizam  uma  API  RESTful.  Dentre  esses princípios temos: sistema no formato cliente-servidor, comunicação stateless;  capacidade  de  armazenar  dados  em  cache;  interface uniforme e sistema de camadas; 

11. **Testes:** 
1. **Rest  Assured:**  Rest-Assured  é  uma  ferramenta  que  foi desenvolvida  para  facilitar  a criação de testes automatizados para  APIs  REST.  Esta  oferece  suporte  para  validar  protocolo HTTP e requisições em JSON. O Rest Assured oferece também extensas  opções  de  validações  das  requisições  que  são enviadas nos serviços REST, tais como: Status Code, Headers e também  elementos  do  Body.  Tornando  a  ferramenta extremamente  flexível  para  utilizar  na  criação  de  testes automatizados de API; 
1. **Testes de Fumaça:** O ciclo de teste de fumaça que é realizado pelas equipes de QA no ambiente de teste garante que se o edifício implantado está atingindo a meta principal e se deve ou  não  ser  testado  posteriormente.  Em outras palavras, se a construção  falhar  no  cenário  de  teste  da  funcionalidade central, então ela será rejeitada imediatamente pela equipe de QA, e eles deixarão de testá-la. Ele é elaborado para garantir a funcionalidade central do novo build. Este tipo de processo e método  de  verificação  também  verifica  a  estabilidade  e  o aspecto funcional completo da construção e se falhar no teste de fumaça, então não há sentido em prosseguir com o teste; 
1. **Teste  de  Regressão:**  O teste de regressão é uma técnica do teste  de  software que consiste na aplicação de versões mais recente  do  software,  para  garantir  que  não  surgiram  novos defeitos em componentes já analisados. Se, ao juntar o novo 

componente  ou  as  suas  alterações  com  os  componentes restantes  do  sistema  surgirem  novos  defeitos  em componentes  inalterados,  então  considera-se  que  o  sistema regrediu; 

4. **Teste  Unitário:**  São  testes  que  verificam  se  uma  parte específica do código, costumeiramente a nível de função, está funcionando  corretamente.  Em  um  ambiente  orientado  a objetos é usualmente a nível de classes e a mínima unidade de testes inclui ![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.003.png)construtores e destrutores; 
4. Esta é a nossa **Pla[nilha** de to](https://docs.google.com/spreadsheets/d/1Ao7xNIRpqUyMUbLUS21Dlr9fgWTlM2QrokSb-jDmBOM/edit?usp=sharing)**dos os nossos Testes Manuais. 
12. **Frameworks:** 
1. O  Swagger  é  um  framework  composto  por  diversas ferramentas  que,  independente  da  linguagem,  auxilia  a descrição,  consumo  e  visualização  de  serviços  de  uma  API REST; 
1. O  Spring  Boot  é  um  framework  Java  open  source  que  tem como  objetivo  facilitar  esse  processo  em  aplicações  Java. Consequentemente, ele traz mais agilidade para o processo de desenvolvimento, uma vez que os devs conseguem reduzir o tempo gasto com as configurações iniciais. 

**XIII.  Jira:** 

É  uma  ferramenta  que  permite  o  monitoramento  de  tarefas e acompanhamento  de  projetos  garantindo  o  gerenciamento  de todas as suas atividades em único lugar; 

**XIV.  Scrum:** 

É uma estrutura para gestão de projetos que ajuda as equipes a organizar  e  gerenciar  o  trabalho  por  m eio  de  um  conjunto  de valores,  princípios  e  práticas.  Trata-se de um método de trabalho realizado  a  partir  de  pequenos  ciclos  que  buscam  otimizar  e garantir que tudo seja feito da m elhor forma possível e em tempo hábil; 

Links úteis ![](Aspose.Words.2eccba6c-d412-4cf6-bd61-ef1cdaf18169.010.png)

1. [Repositório do Projeto no GitHub ](https://github.com/jardel978/projeto-integrador-2-backend)
1. [Pitch - Apresentação do Projeto ](https://docs.google.com/presentation/d/1_pvoxhy2wVSSdYDK7Pmr2q_YTYzkobCBmY8ucSOPsxE/edit#slide=id.g22c7befabde_0_51)

III. 
