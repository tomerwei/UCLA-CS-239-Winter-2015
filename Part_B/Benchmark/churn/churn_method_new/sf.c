#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_BUFFER_SIZE 1000
#define MAX_NESTED_BRACKET 200

int bracket_pointer = 0;
enum bracket
{
	left,
	right
} bracket_stack[MAX_NESTED_BRACKET];

char prev_buffer[MAX_BUFFER_SIZE];
char buffer[MAX_BUFFER_SIZE];
char filename[MAX_BUFFER_SIZE];
char funcname[MAX_BUFFER_SIZE];
char s[MAX_BUFFER_SIZE];
char tmp[MAX_BUFFER_SIZE];

static inline islikely_func(char * name)
{
	return (strstr(buffer, "(") && strstr(buffer, ")") && !strstr(buffer, ";") && !strstr(buffer, "*") && !strstr(buffer, "//"));
}

//Add static and inline to make sure it is compiled as inline.
static inline void remove_char(char * a, char b)
{
	char * src = a, * dst = a;
	while (*dst = * src++) if (*dst != b) dst++;
}

static inline void trim_func_name(char * name)
{
	remove_char(name,' ');
	remove_char(name, '('); remove_char(name, ')'); remove_char(name, ','); remove_char(name, '{');
}

static inline void replace_char(char * a, char b, char c)
{
	while (*a)
	{
		*a = (*a == b ? c : *a);
		a++;
	}
}

int main(int argc, char * argv[])
{
	int state = 0, prev_state = 0;
	freopen(argv[1], "r", stdin);
	while (scanf("%s", &s) != EOF)
	{
		FILE * input = fopen(s, "r"), * output = NULL;
		char * ptr = NULL;
		state = 0;
		while (fgets(buffer, MAX_BUFFER_SIZE, input))
		{
//			printf("%s\n", buffer);
			if (strstr(buffer, "/*") || strstr(buffer, "/**"))
			{
				prev_state = state;
				state = -1;
				continue;
			}

//			printf("%d\n", state);

			switch (state)
			{

				case -1:
					if (strstr(buffer, "*/")) state = prev_state;
					break;
				case 0:
					if (strstr(buffer, "{"))
					{
						bracket_stack[bracket_pointer++] = left;
						if (bracket_pointer == 2)
						{
							char * ptr = NULL;
							if (islikely_func(buffer)) ptr = buffer;
							else if (islikely_func(prev_buffer)) ptr = prev_buffer;
							else
							{
								printf("Die between\n %s\n %s\n", buffer, prev_buffer);
								exit(-1);
							}
							strcpy(filename, argv[2]);

							strcpy(tmp, s);
							(*strstr(tmp, ".java")) = '\0';
							replace_char(tmp, '/', '.');

//							sscanf(ptr, "%s", &funcname);
							strcpy(funcname, ptr);
							trim_func_name(funcname);

							strcat(filename, tmp);
							strcat(filename, funcname);
							strcat(filename, ".java");

							output = fopen(filename, "w");
							fprintf(output, ptr);
							if (ptr == prev_buffer) fprintf(output, buffer);
							state = 1;
						}
					}

					if (strstr(buffer, "}"))
					{
						if (bracket_pointer == 0)
						{
							printf("Error in bracket matching!\n");
							exit(-1);
						}
						bracket_pointer --;
						if (bracket_pointer == 1) state = 0;
					}
					break;
				case 1:
//					printf("%s\n", buffer);
//					printf("ouput = %ld\n", (long) output);
					fprintf(output, "%s\n", buffer);
					if (strstr(buffer, "{")) bracket_stack[bracket_pointer++] = left;
					if (strstr(buffer, "}"))
					{
						bracket_pointer--;
						if (bracket_pointer == 1) state = 0;
					}
			}
			strcpy(prev_buffer, buffer);
		}
		fclose(input);
		fclose(output);
	}
}
