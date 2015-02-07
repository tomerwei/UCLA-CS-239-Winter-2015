#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_AUTHOR_NAME 100
#define MAX_AUTHOR 20
#define MAX_DATE  100
#define MAX_COMMIT 20000
#define MAX_LINE_LENGTH 500

int author_name_list_len = 0;
char author_name_list[MAX_AUTHOR][MAX_AUTHOR_NAME];
char buffer[MAX_LINE_LENGTH];
char author[MAX_AUTHOR_NAME];

#define assert_string_in_buffer(__buffer, __string) \
do \
{ \
	if (strstr(__string, __buffer)) \
	{ \
		fprintf(stderr, "The string %s does not contain "__string"!\n", __buffer); \
		goto exit; \
	}\
} \
while (0)

char * month_to_int[12] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

struct commit
{
	int author, epoch, file;
} commit[MAX_COMMIT];

inline int cmp(struct commit * c1, struct commit * c2)
{
	return c1->author == c2->author ? -(c1->epoch - c2->epoch) : c1->author - c2->author;
}

void myqsort(int i, int j)
{
	struct commit commit_mid = commit[(i + j)/2];
	int i1 = i, j1 = j;
	while (i < j)
	{
		while (cmp(&commit[i], &commit_mid) < 0) i++;
		while (cmp(&commit[j], &commit_mid) > 0) j--;
		if (i <= j)
		{
			struct commit tmp = commit[i];
			commit[i++] = commit[j]; commit[j--] = tmp;
		}
	}
	if (i < j1) myqsort(i, j1);
	if (i1 < j) myqsort(i1, j);
}


int main(int argc, char * argv[])
{
	int commit_count = 0, current_author = 0, i  = 0;
	long long total_time = 0;
	freopen(argv[1], "r", stdin);
	while (gets(buffer))
	{
		char weekday[5], month[5];
		int day = 0,hour = 0,min = 0,sec = 0, year = 0;
		struct tm tm;
loop:
		assert_string_in_buffer(buffer, "Author");

		i = 0;
		sscanf(buffer, "Author: %s", &author);
		while (i < author_name_list_len && strcmp(author_name_list[i], author)) i++;

		if (i < author_name_list_len) commit[commit_count].author = i;
		else
		{
			strcpy(author_name_list[author_name_list_len++], author);
			commit[commit_count].author = author_name_list_len;
		}

		gets(buffer);
		assert_string_in_buffer(buffer, "Date:");

		sscanf(buffer, "Date: %s %s %d %d:%d:%d %d", &weekday, &month, &day, &hour, &min, &sec, &year);

		for (i = 0; i < 12 && !strcmp(month, month_to_int[i]); i++);

		if (i >= 12) fprintf(stderr, "Cannot find the month %s!\n", month);

		tm.tm_sec = sec; tm.tm_min = min; tm.tm_hour = hour; tm.tm_mday = day;
		tm.tm_mon = i; tm.tm_year = year;
		commit[commit_count].epoch = mktime(&tm);

		gets(buffer);

		if (strstr(buffer, "Author")) goto loop;
		else
		{
			int file_change = 0;
			assert_string_in_buffer(buffer, "files changed");
			sscanf(buffer, "%d files changed", &file_change);
			commit[commit_count++].file = file_change;
		}

	}

	myqsort(0, commit_count - 1);


	current_author = commit[0].author;

	for (i = 0; i < commit_count; i++)
	{
		if (commit[i+1].author != current_author)
		{
			current_author = commit[i+1].author;
			continue;
		}
		else
			total_time += (commit[i].epoch - commit[i+1].epoch) * commit[i].file;
	}

	printf("%lld\n", total_time);
	return 0;

exit:
	fprintf(stderr, "Execution failed!\n");
	return -1;
}
