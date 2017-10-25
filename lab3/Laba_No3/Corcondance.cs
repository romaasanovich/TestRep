using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laba_No3
{
    class Data
    {
        public string word;
        public int numOfSent;
        static public int MyClassCompareDate(Data mf1, Data mf2)
        {
            return mf1.word.CompareTo(mf2.word);
        }
    }

    class InfoCorc
    {
        public string word;
        public List<int> sentences = new List<int>();
    }

    class Corcondance
    {
        string alphabet = "abcdefghijklmnopqrstuvwqyz";
        public Processing pr = new Processing();
        public List<Data> words = new List<Data>();
        List<InfoCorc> info = new List<InfoCorc>();

        public void CreateListWords()
        {
            pr.ProcessingText();
            for (int i = 0; i < pr.text.sentences.Count; i++)
            {
                for (int j = 0; j < pr.text.sentences[i].words.Count; j++)
                {
                    Data data = new Data();
                    data.word = (pr.text.sentences[i].words[j].word).ToLower();
                    data.numOfSent = i + 1;
                    words.Add(data);
                }
            }
            SortWords();
        }

        public void SortWords()
        {
            words.Sort(Data.MyClassCompareDate);
        }


        public void Output()
        {
            for (int i = 0; i < words.Count; i++)
            {
                Console.WriteLine(words[i].word + "|" + words[i].numOfSent);
            }
        }


        public void CreateCorcondance()
        {
            
            bool firstInput = true;
            string temp = words[12].word;
            InfoCorc element = new InfoCorc();
            for (int i = 12, j = 0; i < words.Count(); i++)
            {
                if (words[i].word[0] != alphabet[j])
                    j++;
                if (words[i].word[0] == alphabet[j])
                {
                    temp = words[i].word;
                    for (; i < words.Count();)
                    { 
                        if (words[i].word == temp)
                        {
                            if (firstInput == true)
                            {
                                element.word = words[i].word;
                                firstInput = false;
                            }
                            element.sentences.Add(words[i].numOfSent);
                            i++;
                        }
                        else
                        {
                            info.Add(element);
                            element = new InfoCorc();
                            element.sentences.Clear();
                            element.word = null;
                            i--;
                            firstInput = true;
                            
                            break;
                        }
                    }
                }
            }
        }



        public void ShowCorcondance()
        {
            string outputSent(int j)
            {
                string temp="";
                for(int i = 0; i < info[j].sentences.Count; i++)
                {
                    temp += info[j].sentences[i];
                    if (i != info[j].sentences.Count - 1)
                        temp += ", ";
                }
                return temp;
            }

            for(int i =0;i< info.Count(); i++)
            {
                Console.WriteLine(info[i].word + "......." + info[i].sentences.Count + ": " + outputSent(i));
            }
        

        }



    }
}





