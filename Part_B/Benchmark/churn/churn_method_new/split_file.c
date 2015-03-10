#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_NESTED_BRACKET 200


int bracket_pointer = 0;
enum bracket
{
	left,
	right
} bracket_stack[MAX_NESTED_BRACKET];


#define MAX_FILE_NAME       100
#define MAX_BUFFER 	  		1000
#define MAX_FUNCTION_NAME 	1000
#define MAX_TEMP            100
char s[MAX_FILE_NAME];
char buffer[MAX_BUFFER];

char function_name_buffer[MAX_BUFFER];

char temp[MAX_TEMP];
char function_name[MAX_FUNCTION_NAME];

char output_name[MAX_BUFFER];
int state = 0;

//Add static and inline to make sure it is compiled as inline.
static inline void remove_char(char * a, char b)
{
	char * src = a, * dst = a;
	while (*dst = * src++) if (*dst != b) dst++;
}

static inline void replace_char(char * a, char b, char c)
{
	while (*a)
	{
		*a = (*a == b ? c : *a);
		a++;
	}
}

//argv[1]: input file
//argv[2]: output file dir
//Ad-hoc java function parser.
int main(int argc, char * argv[])
{
	freopen(argv[1], "r", stdin);
start:
	while (scanf("%s", &s) != EOF)
	{
		FILE * input = fopen(s, "r"), * output = NULL;
		char * ptr = NULL;
		state = 0;
		while (fgets(buffer, MAX_BUFFER, input))
		{
			switch (state)
			{
				case 0:
//					fprintf(stderr, "buffer in state0 = %s\n", buffer);
					if (strstr(buffer, "(") && strstr(buffer, ")") && !strstr(buffer, ";") && !strstr(buffer, "*") && !strstr(buffer, "//"))
					{
						char * tmp_buffer = buffer;
						strcpy(function_name_buffer, buffer);
						state = 1;

						strcpy(function_name, s);
						replace_char(function_name, '/', '.');

						while (sscanf(tmp_buffer,"%s", &temp) != EOF)
						{
							tmp_buffer = strstr(tmp_buffer, temp) + strlen(temp);
							//Compiler will collapse loop here
							remove_char(temp, '('); remove_char(temp, ')'); remove_char(temp, ','); remove_char(temp, '{');
							strcat(function_name, temp);
						}

						if (strstr(strstr(buffer, ")"), "{")) goto state2;
					}
					break;

				case 1:
//					fprintf(stderr, "buffer in state1 = %s\n", buffer);
					ptr = buffer;
					sscanf(buffer, "%s", &temp);
					if (!strcmp(temp, "{"))
					{
state2:
						ptr = buffer;
						state = 2;
						bracket_pointer = 1;
						bracket_stack[0] = left;
						strcpy(output_name, argv[2]);
						strcat(output_name, function_name);
						strcat(output_name, ".java");
						output = fopen(output_name, "w");
						if (output == NULL)
						{
							fprintf(stderr, "cannot open output_file %s\n", output_name);
							exit(-1);
						}
						fprintf(output, "%s\n", function_name_buffer);
						fprintf(output, "%s\n", buffer);
						if (strstr(ptr, "}"))
						{
							fclose(output);
							state = 0;
						}
					}
					else
					{
						if (strstr(ptr, ";")) { state = 0; goto out; }

						if (strstr(temp, "throws") || strstr(temp, "*")) goto out;

						if (temp[0] != '\0')
						{
							fprintf(stderr, "Failed to find the bracket!\n");
							fprintf(stderr, "Inside file %s\n", s);
							fprintf(stderr, temp);
							exit(-1);
						}
 					}

out:
					break;

				case 2:
//					fprintf(stderr, "buffer in state2 = %s\n", buffer);
					ptr = buffer;
					fprintf(output, "%s\n", buffer);
					while (strstr(ptr, "{") || strstr(ptr, "}"))
					{
						if (     (strstr(ptr, "{") != NULL && strstr(ptr, "{") < strstr(ptr, "}")) || (strstr(ptr, "}") == NULL) )
						{
							ptr = strstr(ptr, "{") + 1;
//							printf("%s\n", ptr);
							bracket_stack[bracket_pointer++] = left;
						}
						else
						if (strstr(ptr, "}") != NULL)
						{
							if (bracket_pointer == 0)
							{
								fprintf(stderr, "Error in matching bracket!\n"); exit(-1);
							}
							else if (--bracket_pointer == 0)
							{
								fclose(output);
								state = 0;
							}
							ptr = strstr(ptr, "}") + 1;
//							printf("%s\n", ptr);
						}
					}
					break;
			}

		}

	}
	return 0;
}
