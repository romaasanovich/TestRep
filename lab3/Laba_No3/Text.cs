using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laba_No3
{
    class Text
    {
        string text;
        public List<Sentences> sentences = new List<Sentences>();
        

        public void ReadText()
        {
            StreamReader read = new StreamReader("Text.txt");
            text = read.ReadToEnd();
            sentences.Sort(new CompareByWordLength());
        }

        public void  DelSpaces()
        {
            text = System.Text.RegularExpressions.Regex.Replace(text, @"\s+", " ");
        }


        public void SmashIntoSentences()
        {
            StringBuilder str = new StringBuilder();
            for (int i =0;i<text.Length;i++)
            {
                str.Append(text[i]);
                if(text[i]=='!' || text[i]=='?' || text[i] == '.')
                {
                    string temp = str.ToString();
                    Sentences s = new Sentences();
                    s.sent = temp;
                    sentences.Add(s);
                    str.Clear();
                    i++;
                }
            }
        }
    }
}
