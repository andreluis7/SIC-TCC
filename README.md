# Projeto de conclusão de curso - SIC - Sistema Interno Corporativo - Fatec Ourinhos - SP
> Repositório dedicado ao projeto de conclusão de curso que consiste em criar uma aplicação denominada de SIC - Sistema Interno Corporativo que busca auxiliar no gerenciamento de ordens de serviço, utilizando as técnologias (JAVA-JSF-PRIMEFACES-JASPER-HIBERNATE-HTML-CSS-JAVASCRIPT-AJAX).

## Artigo científico
O artigo científico sobre o desenvolvimento desse projeto está disponível em: [Link](https://drive.google.com/file/d/1UQFFHbqksym1ciJQn286q5zK-yo5zBKv/view?usp=sharing)

## Projeto

Para facilitar a compreensão o projeto foi dividido em pacotes 
* Bean - Essa especificação define uma API e dita regras de configuração e comunicação entre componentes e convenções de programação.
* Dao	- Abstrai e encapsula os mecanismos de acesso a dados escondendo os detalhes da execução da origem dos dados
* Domain - Classe onde é modelado as tabelas do projeto
* Util - Arquivos utéis, como por exemplo classes de conexao com banco de dados

![](Sistema%20Interno%20Corporativo/src/main/webapp/resources/images/login.png)
Tela inicial do sistema

![](Sistema%20Interno%20Corporativo/src/main/webapp/resources/images/encerramento.png)
Tela de encerramento de chamados

## Instalação

Windows:

```sh
Instalar a IDE eclipe disponível em https://www.eclipse.org/downloads/
Clonar o projeto git clone https://github.com/andreluis7/SIC-TCC.git
Importar projeto no eclipse e executar
Importante verificar a sua versão do mysql pois em alguns casos versões mais antigas podem não funcionar
Alterar configurações do banco de dados que estão no arquivo hibernate.cfg 
Caso rode local mudar para localhost e criar o banco sic no banco de dados
```

## Configuração para Desenvolvimento

A aplicação não possui a necessidade de download de dependência para o seu completo funcionamento, apenas baixar e executar.

## Histórico de últimos commits

* (#49) 
    * [Ajustes nos tamanhos das colunas](https://github.com/andreluis7/SIC-TCC/commit/e7150a47d2e7ea4fb4f2cc8f8bbaf7640d94c3cf)
* (#48) 
    * [Adicionado prioridade e tempo aprox de atendimento na tela de problemas](https://github.com/andreluis7/SIC-TCC/commit/467b87592557131aa10ad05f76e32e4d5dd2cd6a)
* (#47) 
    * [Adicionado fundo transparente nas imagens](https://github.com/andreluis7/SIC-TCC/commit/7b9de11ac1375352f7c41bfc43f018ba19ae654e)
* (#46) 
    * [Atualizando layout do menu e login](https://github.com/andreluis7/SIC-TCC/commit/b7d9e607b8829b9b75239ad30f83f6c1b12b6340)
* (#45) 
    * [Adicionado download do relatório](https://github.com/andreluis7/SIC-TCC/commit/41ff54ff9ef9557e317c5142118cafbf89d249fc)
* (#44) 
    * [(Update imagem da tela principal](https://github.com/andreluis7/SIC-TCC/commit/4b57d47c0d374e819fe194e887b03888ac849eac)

## Meta

André Luís – [@andreluis7](https://www.linkedin.com/in/andreluis7) – adrluis7@gmail.com

Distribuído sob a licença MIT. Veja `LICENSE` para mais informações.

[https://github.com/andreluis7](https://github.com/andreluis7)

## Contributing

1. Faça o _fork_ do projeto (<https://github.com/andreluis7/SIC-TCC/fork>)
2. Crie uma _branch_ para sua modificação (`git checkout -b feature/fooBar`)
3. Faça o _commit_ (`git commit -am 'Add some fooBar'`)
4. _Push_ (`git push origin feature/fooBar`)
5. Crie um novo _Pull Request_

