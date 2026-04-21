%{
#include<stdio.h>
%}

%%

stop exit(0);

[a-zA-Z][a-zA-Z0-9]* printf("\nFound Identifier: %s",yytext);

[0-9]+ printf("\nFound Number: %s",yytext);

%%

main()
{
yylex();
}

int yywrap()
{
	return 1;
}


