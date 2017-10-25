using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laba_No3
{
    class CompareByWordLength : IComparer<Sentences>
    {
        public int Compare(Sentences x, Sentences y)
        {
            throw new NotImplementedException();
        }

    }



    class Sentences
    {
        public string sent;
        public List<Words> words = new List<Words>();
        public string type;
        public char lastSymb;


        public void GetTypeOfSentensces()
        {
            if (sent[sent.Length - 1] == '!')
            {
                type = "exclamatory";
                lastSymb = '!';
            }
            else if (sent[sent.Length - 1] == '?')
            {
                type = "interrogative";
                lastSymb = '?';
            }
            else if (sent[sent.Length - 1] == '.')
            {
                type = "affirmative";
                lastSymb = '.';
            }
        }

        public void CreatingWords()
        {
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < sent.Length; j++)
            {
                if (sent[j] == ' '|| sent[j] == '?' || sent[j] == '!' || sent[j] == '.')
                {
                    string temp = str.ToString();
                    Words w = new Words();
                    w.word = temp;
                    words.Add(w);
                    str.Clear();
                }
                else str.Append(sent[j]);

            }
            
        }
    }
}
