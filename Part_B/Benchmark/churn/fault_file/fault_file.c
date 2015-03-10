#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define assert_string_in_buffer(__buffer, __string) \
do \
{ \
	if (!strstr( __buffer, __string)) \
	{ \
		fprintf(stderr, "The string %s does not contain "__string"!\n", __buffer); \
		exit(-1); \
	}\
} \
while (0)

#define BUFFER_SIZE 10000
char buffer[BUFFER_SIZE];

#define FUNCTION_SIZE 500

#define STRING_NUM 100000
char str[STRING_NUM][FUNCTION_SIZE];


char tmp_str[FUNCTION_SIZE];
char swap[FUNCTION_SIZE];

void myqsort(int i, int j)
{
//	printf("%d %d\n", i, j);
	strcpy(tmp_str, str[(i + j)/2]);
//	printf("%s\n", str[(i + j)/2]);
	int i1 = i, j1 = j;
	while (i < j)
	{
		while (strcmp(str[i], tmp_str) < 0) i++;
		while (strcmp(str[j], tmp_str) > 0) j--;
		if (i <= j)
		{
			strcpy(swap, str[i]);
			strcpy(str[i++], str[j]); strcpy(str[j--], swap);
		}
	}
	if (i < j1) myqsort(i, j1);
	if (i1 < j) myqsort(i1, j);
}



int main(void)
{
	int state = 0, fix = 0, str_num = 0, i = 0, count = 0;
	while (gets(buffer))
	{
		switch (state)
		{
			case 0: assert_string_in_buffer(buffer, "commit"); state = 1; break;
			case 1:
				if (strstr(buffer, "Merge")) break;
				assert_string_in_buffer(buffer, "Author"); state = 2; break;
			case 2: assert_string_in_buffer(buffer, "Date");   state = 3; break;
			case 3:
				if (strstr(buffer, "Fix") || strstr(buffer, "fix")) fix = 1;
				if (strstr(buffer, "|"))
				{
					if (fix)
					{
						char * ptr = strstr(buffer, ".java");
						if (ptr)
						{
							*(ptr+5) = '\0';
							strcpy(str[str_num++], buffer);
						}
					}
				}
				if (strstr(buffer, "files changed") && strstr(buffer, "insertions") && strstr(buffer, "deletions"))
				{
					 fix = 0;
					 state = 4;
				}
				break;
		  case 4:
			  state = 0;
		}
	}

	myqsort(0, str_num - 1);

	strcpy(tmp_str, "");
	for (i = 0; i < str_num; i++)
	{
		if (strcmp(str[i], tmp_str))
		{
			printf("%d %lf\n", count, count * 1.0 / str_num);
			strcpy(tmp_str, str[i]);
			printf("%s ", str[i]);
			count = 1;
		}
		else count++;	
	}

	return 0;
}
