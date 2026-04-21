%{
#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
%}

%%

stop        { exit(0); }

username    { printf("\n%s", getlogin()); }

.           { /* ignore everything else */ }

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