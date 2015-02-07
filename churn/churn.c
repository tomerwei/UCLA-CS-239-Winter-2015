#include <stdio.h>
#include <stdlib.h>
#define MAX_LENGTH_LINE 500
int main(int argc, char * argv[])
{
	int file_change = 0, insertion = 0, deletion = 0, sum_file_change = 0, sum_insertion = 0, sum_deletion  = 0;
	char s[MAX_LENGTH_LINE];
	freopen(argv[1], "r", stdin);
	while (gets(s))
	{
		sscanf(s, "%d files changed, %d insertions(+), %d deletions(-)", &file_change, &insertion, &deletion);
		printf("%d %d %d\n", file_change, insertion, deletion);
		sum_file_change += file_change;
		sum_insertion += insertion;
		sum_deletion += deletion;
	}
	printf("file_change = %d, total change = %d, insertion = %d, deletion = %d\n", sum_file_change, sum_insertion + sum_deletion, sum_insertion, sum_deletion);
}
