using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using System.Text;
using System.Threading.Tasks;

namespace Laba_No3
{
    class Processing
    {
        public Text text = new Text();
        public void ProcessingText()
        {
            text.ReadText();
            text.DelSpaces();
            text.SmashIntoSentences();
            for (int i = 0; i < text.sentences.Count; i++)
            {
                text.sentences[i].CreatingWords();
                text.sentences[i].GetTypeOfSentensces();
                for (int j = 0; j < text.sentences[i].words.Count(); j++)
                {
                    text.sentences[i].words[j].ProcWords();
                }
            }
        }

        public void OutputSentencesAscendingWords()
        {
            Text temp = new Text();
            temp = text;
            for (int i = 0; i < temp.sentences.Count; i++)
            {
                for (int j = 0; j < temp.sentences.Count - 1; j++)
                {
                    if (temp.sentences[j].words.Count > temp.sentences[j + 1].words.Count)
                    {
                        Text z = new Text();
                        Sentences s = new Sentences();
                        s.sent = "";
                        z.sentences.Add(s);
                        z.sentences[0] = temp.sentences[j];
                        temp.sentences[j] = temp.sentences[j + 1];
                        temp.sentences[j + 1] = z.sentences[0];
                    }
                }
            }

            for (int i = 0; i < temp.sentences.Count; i++)
            {
                Console.WriteLine(temp.sentences[i].sent);
            }

        }

        public void OutputWordsWithChoosenLength()
        {
            Console.Write("Input Length:");
            string s;
            s = Console.ReadLine();
            int n = Convert.ToInt32(s);
            List<string> words = new List<string>();
            for (int i = 0; i < text.sentences.Count; i++)
            {
                if (text.sentences[i].type == "interrogative")
                {
                    for (int j = 0; j < text.sentences[i].words.Count; j++)
                    {
                        if (text.sentences[i].words[j].word.Length == n && !(words.Contains(text.sentences[i].words[j].word)))
                        {
                            words.Add(text.sentences[i].words[j].word);
                            Console.WriteLine(text.sentences[i].words[j].word);
                        }
                    }
                }
            }

        }

        public void DelWordsWhichBeginWithConsonantLetter()
        {
            Console.Write("Input Length:");
            string s;
            s = Console.ReadLine();
            int n = Convert.ToInt32(s);
            for (int i = 0; i < text.sentences.Count; i++)
            {
                string pattern = "^(?i:[bcdfghjklmnopqrstvwxz]).*";
                Regex regex = new Regex(pattern);
                for (int j = 0; j < text.sentences[i].words.Count; j++)
                {
                    Match match = regex.Match(text.sentences[i].words[j].word);
                    if (match.Success && text.sentences[i].words[j].word.Length == n)
                    {
                        text.sentences[i].words.Remove(text.sentences[i].words[j]);
                    }
                }
            }
            OutputAllSent();
        }


        public void OutputAllSent()
        {
            for (int i = 0; i < text.sentences.Count; i++)
            {
                for (int j = 0; j < text.sentences[i].words.Count; j++)
                    Console.Write(text.sentences[i].words[j].before + text.sentences[i].words[j].word + text.sentences[i].words[j].after + " ");
                Console.Write(text.sentences[i].lastSymb);
                Console.WriteLine();
            }
        }

        public void OutputSent(int i)
        {
            for (int j = 0; j < text.sentences[i].words.Count; j++)
                Console.Write(text.sentences[i].words[j].before + text.sentences[i].words[j].word + text.sentences[i].words[j].after + " ");
            Console.Write(text.sentences[i].lastSymb);
            Console.WriteLine();
        }

        public void ReplaceInSent()
        {
            Console.Write("Input Num of Sent:");
            string num;
            num = Console.ReadLine();
            int n = Convert.ToInt32(num) - 1;
            string w= "qwer";

            OutputSent(n);


            Console.Write("Input Length:");
            string s;
            s = Console.ReadLine();
            int l = Convert.ToInt32(s);
            for (int i = 0; i < text.sentences[n].words.Count; i++) 
            {
                if(text.sentences[n].words[i].word.Length==l)
                {
                    text.sentences[n].words[i].word = w;
                }
            }
            OutputSent(n);
        }

    }

}
