%{
#include<stdio.h>
#include<stdlib.h>
%}

%%

stop    { exit(0); }

[a-zA-Z][a-zA-Z0-9]*   { printf("\nFound Identifier: %s", yytext); }

[0-9]+   { printf("\nFound Number: %s", yytext); }

%%

int main()
{
    yylex();
    return 0;
}

int yywrap()
{
    return 1;
}